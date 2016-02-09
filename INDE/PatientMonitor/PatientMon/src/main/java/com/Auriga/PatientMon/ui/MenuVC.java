package com.Auriga.PatientMon.ui;

import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.NInt;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.foundation.NSIndexPath;
import ios.uikit.UITableView;
import ios.uikit.UITableViewCell;
import ios.uikit.UITableViewController;
import ios.uikit.UIViewController;

/**
 * Created by alexeybologov on 8/2/15.
 */

enum MenuSections{
        AudioSection,
        TimerSection,
        PatientSection,
        CloseMenuSection,
        MenuSectionsNumber
        };

enum AudioSectionEnum {
        AudioSectionPause,
        AudioSectionNPB,
        AudioSectionAlarm,
        AudioSectionNumber
        };

enum TimerSectionEnum {
        TimerSectionDischarge,
        TimerSectionCaseTimer,
        TimerSectionNumber
        };

enum PatientSectionEnum {
        PatientSectionData,
        PatientSectionProcedures,
        PatientSectionReports,
        PatientSectionSetup,
        PatientSectionLayoutSetup,
        PatientSectionMainScreen,
        PatientSectionNumber
        };

@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("MenuVC")
@RegisterOnStartup
public class MenuVC extends UITableViewController {
    static {
        NatJ.register();
    }

    @Selector("alloc")
    public static native MenuVC alloc();

    @Selector("init")
    public native MenuVC init();

    @Generated("NatJ")
    protected MenuVC(Pointer peer) {
        super(peer);
    }

    public UIViewController parentController;

    /*******************************************
     * UITableViewDelegate
     *******************************************/

    @Selector("numberOfSectionsInTableView:")
    @Override
    @NInt
    public long numberOfSectionsInTableView(UITableView tableView) {
        return MenuSections.valueOf("MenuSectionsNumber").ordinal();
    }

    @Selector("tableView:numberOfRowsInSection:")
    @Override
    @NInt
    public long tableViewNumberOfRowsInSection(UITableView tableView, long section)
    {
        MenuSections[] allSections = MenuSections.values();
        MenuSections sect = allSections[(int)section];
        switch (sect) {

            case PatientSection:return PatientSectionEnum.valueOf("PatientSectionNumber").ordinal();

            case AudioSection: return AudioSectionEnum.valueOf("AudioSectionNumber").ordinal();

            case TimerSection: return TimerSectionEnum.valueOf("TimerSectionNumber").ordinal();

            case CloseMenuSection: return 1;

            default: assert(false);
        }
        return 0;
    }


    @Selector("tableView:cellForRowAtIndexPath:")
    @Override
    public UITableViewCell tableViewCellForRowAtIndexPath(UITableView tableView, NSIndexPath indexPath) {
        String reusableId = "menuIdentifier";
        UITableViewCell cell = (UITableViewCell)this.tableView().dequeueReusableCellWithIdentifierForIndexPath(reusableId, indexPath);

        String cellText = "";

        MenuSections[] allSections = MenuSections.values();
        MenuSections sect = allSections[(int)indexPath.section()];
        switch (sect) {

            case CloseMenuSection:
                cellText = "Close Menu" ;
                break;

            case PatientSection:{
                PatientSectionEnum[] allPatientSections = PatientSectionEnum.values();
                PatientSectionEnum row = allPatientSections[(int)indexPath.row()];
                switch (row) {

                    case PatientSectionData: cellText = "Patient Data"; break;
                    case PatientSectionProcedures: cellText = "Procedures"; break;
                    case PatientSectionReports: cellText = "Reports"; break;
                    case PatientSectionSetup: cellText = "Patient Setup"; break;
                    case PatientSectionLayoutSetup: cellText = "Layout & Setup"; break;
                    case PatientSectionMainScreen: cellText = "Main Screen"; break;

                    default: assert(false);
                }
                break;
            }

            case TimerSection:{
                TimerSectionEnum[] allTimerSections = TimerSectionEnum.values();
                TimerSectionEnum row = allTimerSections[(int)indexPath.row()];
                switch (row) {

                    case TimerSectionCaseTimer: cellText = "Case Timer"; break;
                    case TimerSectionDischarge: cellText = "Discharge"; break;

                    default: assert(false);
                }
                break;
            }

            case AudioSection:{
                AudioSectionEnum[] allAudioSections = AudioSectionEnum.values();
                AudioSectionEnum row = allAudioSections[(int)indexPath.row()];
                switch (row) {

                    case AudioSectionPause: cellText = "Audio Pause"; break;
                    case AudioSectionNPB: cellText = "NBP Start / Stop"; break;
                    case AudioSectionAlarm: {
                    /* TODO: Connect this cell with alarm.
                     May be it is not needed: It seems that this menu point from standart patient monitor
                     device without touch screen. Need discuss with ES.
                     */

                        cellText = "Alarm OFF" ;
                        break;
                    }

                    default: assert(false);
                }
                break;
            }


            default: assert(false);
        }

        cell.textLabel().setText(cellText);

        return cell;
    }

    @Selector("tableView:didSelectRowAtIndexPath:")
    @Override
    public void tableViewDidSelectRowAtIndexPath(UITableView tableView, NSIndexPath indexPath) {
        MenuSections[] allSections = MenuSections.values();
        MenuSections sect = allSections[(int)indexPath.section()];
        switch (sect) {
            case CloseMenuSection:{
                this.dismissViewControllerAnimatedCompletion(true, null);
            }
            break;
            case PatientSection:{
                PatientSectionEnum[] allPatientSections = PatientSectionEnum.values();
                PatientSectionEnum row = allPatientSections[(int)indexPath.row()];
                switch (row) {
                    case PatientSectionMainScreen: {
                        this.dismissViewControllerAnimatedCompletion(false, null);
                        this.parentController.navigationController().popToRootViewControllerAnimated(true);
                        break;
                    }

                }
                break;
            }

            default: // Do nothing
                break;
        }
    }
}
