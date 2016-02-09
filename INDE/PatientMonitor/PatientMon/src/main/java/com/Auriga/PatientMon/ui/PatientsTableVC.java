package com.Auriga.PatientMon.ui;

import com.Auriga.PatientMon.logic.PatientInfo;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.NInt;
import com.intel.inde.moe.natj.general.ann.Owned;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Selector;

import java.util.ArrayList;
import java.util.Date;

import ios.NSObject;
import ios.foundation.NSIndexPath;
import ios.uikit.UIStoryboardSegue;
import ios.uikit.UITableView;
import ios.uikit.UITableViewCell;
import ios.uikit.UITableViewController;

/**
 * Created by alexeybologov on 8/2/15.
 */

@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("PatientsTableVC")
@RegisterOnStartup
public class PatientsTableVC extends UITableViewController {

    static {
        NatJ.register();
    }

    @Generated("NatJ")
    @Owned
    @Selector("alloc")
    public static native PatientsTableVC alloc();

    @Generated("NatJ")
    @Owned
    @Selector("init")
    public native PatientsTableVC init();

    @Generated("NatJ")
    protected PatientsTableVC(Pointer peer) {
        super(peer);
    }

    private ArrayList<PatientInfo> mPatients = new ArrayList<PatientInfo>();

    @Selector("prefersStatusBarHidden")
    @Override
    public boolean prefersStatusBarHidden() {
        return true;
    }

    @Selector("viewDidLoad")
    @Override
    public void viewDidLoad() {
        navigationController().navigationBar().setHidden(false);

        PatientInfo p = new PatientInfo();
        p.setFileName("Paul");
        p.setLastName("Smith");
        p.setBed("28");
        p.setPatientID("12312");
        p.setWeight("67 kg");
        p.setHeight("182 cm");
        p.setBirthDate(new Date((new Date()).getTime() - (32l * 365 * 24 * 60 * 60 * 1000) - (100l * 24 * 60 * 60 * 1000)));
        mPatients.add(p);

        p = new PatientInfo();
        p.setFileName("John");
        p.setLastName("Doe");
        p.setBed("42");
        p.setPatientID("98763");
        p.setWeight("78 kg");
        p.setHeight("167 cm");
        p.setBirthDate(new Date((new Date()).getTime() - (58l * 365 * 24 * 60 * 60 * 1000) + (60l * 24 * 60 * 60 * 1000)));
        mPatients.add(p);

        setTitle("Select patient:");
    }

    @Selector("viewWillAppear")
    @Override
    public void viewWillAppear(boolean animated) {

    }

    /*******************************************
     * UITableViewDelegate
     *******************************************/

    @Selector("numberOfSectionsInTableView:")
    @Override
    @NInt
    public long numberOfSectionsInTableView(UITableView tableView) {
        return 1;
    }

    @Selector("tableView:numberOfRowsInSection:")
    @Override
    @NInt
    public long tableViewNumberOfRowsInSection(UITableView tableView, long section) {
        return mPatients.size();
    }

    @Selector("tableView:cellForRowAtIndexPath:")
    @Override
    public UITableViewCell tableViewCellForRowAtIndexPath(UITableView tableView, NSIndexPath indexPath) {

        String reusableId = "patientCell";
        UITableViewCell cell = (UITableViewCell) tableView.dequeueReusableCellWithIdentifierForIndexPath(reusableId, indexPath);
        PatientInfo patient = mPatients.get((int) indexPath.row());
        cell.textLabel().setText(patient.description());

        return cell;
    }

    /*******************************************
     * Navigation
     *******************************************/

    @Selector("prepareForSegue:sender:")
    @Generated
    public void prepareForSegueSender(UIStoryboardSegue segue, NSObject sender) {
        NSIndexPath indexPath = tableView().indexPathForSelectedRow();
        PatientInfo patient = mPatients.get((int) indexPath.row());
        MainMonitorVC controller = (MainMonitorVC) segue.destinationViewController();
        controller.setPatient(patient);
    }
}