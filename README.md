# Auriga-patient-monitor-application
Applications developed by Auriga using Intel Multi-OS Engine
v.0.0.2 
05/10/15
=====================================================
Folders structure
====================================================
"INDE/frameworks/iphoneos/"  -  path to the frameworks which are used for real device installation

"INDE/frameworks/iphonesimulator/"  -  path to the frameworks which are used for iOS simulator installation 

"Waves/OpenGLWaves/" - path to source code of native library which is used to draw waves

"INDE/PatientMonitor/" - path to root of the application.

"INDE/PatientMonitor/PatientMon/" - path to iOS module.


=====================================================
How to build and run PatientMonitor application on the iOS Simulator.
=====================================================
1. Prepare environment: 
	1.1 Install Android Studio (https://developer.android.com/sdk/index.html)
	1.2 Install Intel INDE (https://software.intel.com/en-us/articles/multi-os-engine-beta-download)

2. Open PatientMonitor project from "INDE/PatientMonitor/" folder in Android Studio

3. Add new "Run Configuration" to run iOS module on the simulator:
	3.1 Click on the configuration list (it should already contain "app" text) and select "Edit Configurations..."
	3.2 Press "+" and select "Intel MOE iOS Application"
	3.3 Add name like "iOS simulator"
	3.4 Select "PatientMon" value in section "Module"
	3.5 Select Simulator in "Run on" section
	3.6 Press ok

4. To run project on the iOS simulator we need to use correct waves framework.
	4.1 Please remove file INDE/PatientMonitor/PatientMon/xcode/OpenGLWaves.framework
	4.2 Copy file from INDE/frameworks/iphonesimulator/OpenGLWaves.framework to INDE/PatientMonitor/PatientMon/xcode/ folder.

5. Select "iOS simulator" configuration in the configuration list and press run button. 
Application will be builded and installed on the simulator. Simulator will start automatically.
=====================================================



=====================================================
 How to build and run PatientMonitor application on the real device.
=====================================================
1. Prepare environment: 
	1.1 Install Android Studio (https://developer.android.com/sdk/index.html)
	1.2 Install Intel INDE plugin (https://software.intel.com/en-us/articles/multi-os-engine-beta-download)

2. Open PatientMonitor project from "INDE/PatientMonitor/" folder in Android Studio

3. Connect your iOS device

4. Add new "Run Configuration" to run iOS module on the real device:
	3.1 Click on the configuration list and select "Edit Configurations..."
	3.2 Press "+" and select "Intel MOE iOS Application"
	3.3 Add name like "iOS device"
	3.4 Select "PatientMon" value in section "Module"
	3.5 Select your device in "Run on" section
	3.6 Press ok

5. To run project on the real device we need to use correct waves framework.
	5.1 Please remove file INDE/PatientMonitor/PatientMon/xcode/OpenGLWaves.framework
	5.2 Copy file from INDE/frameworks/iphoneos/OpenGLWaves.framework to INDE/PatientMonitor/PatientMon/xcode/ folder.

6. Select "iOS device" configuration in the configuration list and press run button. 
Application will be builded and installed on the device.
=====================================================

