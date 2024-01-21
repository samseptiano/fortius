package com.samseptiano.fortius.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.samseptiano.fortius.data.dataStore.UserPreferences
import com.samseptiano.fortius.data.model.response.AbsensionModel
import com.samseptiano.fortius.databinding.ActivityHomeBinding
import com.samseptiano.fortius.ui.custom.CustomCameraActivity
import com.samseptiano.fortius.ui.custom.CustomCameraActivity.Companion.URL_DATA
import com.samseptiano.fortius.ui.viewmodel.HomeViewModel
import com.samseptiano.fortius.utils.Constant.Companion.STATUS_CHECK_IN
import com.samseptiano.fortius.utils.Constant.Companion.STATUS_CHECK_OUT
import com.samseptiano.fortius.utils.DateUtil.calculateTimeDifference
import com.samseptiano.fortius.utils.DateUtil.getCurrentDate
import com.samseptiano.fortius.utils.DateUtil.getCurrentDateFormatted
import com.samseptiano.fortius.utils.DateUtil.getCurrentDateTimeStamp
import com.samseptiano.fortius.utils.DateUtil.getCurrentTime
import com.samseptiano.fortius.utils.saveUriToFolder
import com.samseptiano.base.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.Instant
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(), LocationListener {

    private lateinit var viewModel: HomeViewModel
    private var strLocation = ""
    private lateinit var locationManager: LocationManager
    private var latitude = 0.0
    private var longitude = 0.0
    private var imgPhotoBitmap: Bitmap? = null
    private var strPhoto: String? = null

    private var strEmployeeId = ""
    private var strEmail = ""
    private var strRole = ""
    private var strCompanyId = ""
    private var strCompanyName = ""
    private var strName = ""

    @Inject
    lateinit var userPreferences: UserPreferences

    private val requestMultiplePermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allPermissionsGranted = permissions.all { it.value }
            if (allPermissionsGranted) {
                getLocation()
            }
        }

    private val cameraImagelauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val receivedData = Uri.parse(result.data?.getStringExtra(URL_DATA))

                strPhoto = "attendance_${strEmployeeId}_${getCurrentDateTimeStamp()}.jpg"
                val fullPath = saveUriToFolder(receivedData, contentResolver, strPhoto!!)

                insertAttendance(fullPath)

                try {
                    imgPhotoBitmap =
                        MediaStore.Images.Media.getBitmap(contentResolver, receivedData)
                } catch (e: IOException) {
                    Toast.makeText(
                        applicationContext,
                        "Error While convert to bitmap: " + e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityHomeBinding =
        ActivityHomeBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setupViews()
        requestMultiplePermissions()
        setupObserver()
        showUserPerefences()
    }

    private fun insertAttendance(photoPath: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val status =
                if (viewModel.isAlreadyCheckIn(getCurrentDate())) STATUS_CHECK_OUT else STATUS_CHECK_IN

            val listAttendance = arrayListOf<AbsensionModel>()
            listAttendance.add(
                AbsensionModel(
                    Id = Instant.now().epochSecond,
                    employeeId = strEmployeeId,
                    email = strEmail,
                    name = strName,
                    role = strRole,
                    companyId = strCompanyId,
                    companyName = strCompanyName,
                    date = getCurrentDate(),
                    time = getCurrentTime(),
                    status = status,
                    lat = latitude,
                    lon = longitude,
                    location = strLocation,
                    photoPath = photoPath
                )
            )
            viewModel.insertAbsension(listAttendance)
            setupObserver()
        }

    }

    private fun setupViews() {
        binding.apply {
            headerCheckout.apply {
                tvCurrentDate.text = getCurrentDateFormatted()
                btnCheckIn.setOnClickListener {
                    takePhoto()
                }
            }

            btnAttendance.setOnClickListener {
                startActivity(Intent(this@HomeActivity, AbsensionListActivity::class.java))
            }

            tvLogout.setOnClickListener {
                logout()
            }

        }
    }

    private fun takePhoto() {
        val intent = Intent(applicationContext, CustomCameraActivity::class.java)
        cameraImagelauncher.launch(intent)

    }

    private fun init() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private fun showUserPerefences() {

        userPreferences.apply {
            var strRole = ""

            companyName.asLiveData().observe(this@HomeActivity) {
                strCompanyName = it ?: "-"
            }
            companyID.asLiveData().observe(this@HomeActivity) {
                strCompanyId = it ?: "-"
            }
            email.asLiveData().observe(this@HomeActivity) {
                strEmail = it ?: "-"
            }
            employeeId.asLiveData().observe(this@HomeActivity) {
                strEmployeeId = it ?: "-"
            }

            employeeId.asLiveData().observe(this@HomeActivity) {
                strEmployeeId = it ?: "-"
            }
            name.asLiveData().observe(this@HomeActivity) {
                binding.headerHome.tvName.text = it
                strName = it ?: "-"
            }
            role.asLiveData().observe(this@HomeActivity) {
                strRole = it ?: ""
                binding.headerHome.tvRole.text = strRole
            }
            companyName.asLiveData().observe(this@HomeActivity) {
                strRole += (" | $it")
                binding.headerHome.tvRole.text = strRole
            }
        }

    }

    private fun logout() {
        showProgress()
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.clearPreferences()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getAbsension(null, getCurrentDate(), STATUS_CHECK_IN)
            viewModel.getAbsension(null, getCurrentDate(), STATUS_CHECK_OUT)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getLocationCheckInData.observe(this@HomeActivity) {
                strLocation = it.locality ?: ""
            }

            viewModel.getAbsensionCheckOutData.observe(this@HomeActivity) {
                binding.headerCheckout.tvCheckoutTime.text = it.time
                binding.headerCheckout.tvCheckoutLocation.text = it.location
                binding.headerCheckout.tvDuration.text = calculateTimeDifference(
                    binding.headerCheckout.tvCheckinTime.text.toString(),
                    binding.headerCheckout.tvCheckoutTime.text.toString()
                )
            }
            viewModel.getAbsensionCheckInData.observe(this@HomeActivity) {
                binding.headerCheckout.tvCheckinTime.text = it.time
                binding.headerCheckout.tvCheckinLocation.text = it.location
                binding.headerCheckout.tvDuration.text = calculateTimeDifference(
                    binding.headerCheckout.tvCheckinTime.text.toString(),
                    binding.headerCheckout.tvCheckoutTime.text.toString()
                )
            }

            viewModel.isLogoutSuccess.observe(this@HomeActivity) {
                if (it) {
                    startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                    finish()
                }
                hideProgress()
            }
        }

    }

    private fun getLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 2f, this)
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
        lifecycleScope.launch {
            viewModel.getLocationCheckIn(latitude.toString(), longitude.toString())
        }
        //Toast.makeText(this, "lon lat: $latitude , $longitude", Toast.LENGTH_SHORT).show()
        Log.d("lat long", "$latitude , $longitude")
    }

    override fun showProgress() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgress() {
        binding.apply {
            progressBar.visibility = View.GONE
        }
    }

    private fun requestMultiplePermissions() {
        val permissionsToRequest = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )

        val permissionsNotGranted = permissionsToRequest
            .filter {
                ContextCompat.checkSelfPermission(
                    this,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            }
            .toTypedArray()

        if (permissionsNotGranted.isEmpty()) {
            getLocation()
        } else {
            requestMultiplePermissionsLauncher.launch(permissionsNotGranted)
        }
    }
}