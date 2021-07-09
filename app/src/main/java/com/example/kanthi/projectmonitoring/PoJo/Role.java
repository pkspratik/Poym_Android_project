package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kanthi on 21-feb-18.
 */

public class Role {

    @DatabaseField(columnName = "shgModule")
    @SerializedName("shgModule")
    private String shgModule;

    @DatabaseField(columnName = "shgGroupName")
    @SerializedName("shgGroupName")
    private String shgGroupName;

    @DatabaseField(columnName = "shgFormationDate")
    @SerializedName("shgFormationDate")
    private String shgFormationDate;

    @DatabaseField(columnName = "shgAssociatedHF")
    @SerializedName("shgAssociatedHF")
    private String shgAssociatedHF;

    @DatabaseField(columnName = "shgMeetingInterval")
    @SerializedName("shgMeetingInterval")
    private String shgMeetingInterval;

    @DatabaseField(columnName = "shgSearchForName")
    @SerializedName("shgSearchForName")
    private String shgSearchForName;

    @DatabaseField(columnName = "shgName")
    @SerializedName("shgName")
    private String shgName;

    @DatabaseField(columnName = "shgAddMember")
    @SerializedName("shgAddMember")
    private String shgAddMember;

    @DatabaseField(columnName = "shgIndividualId")
    @SerializedName("shgIndividualId")
    private String shgIndividualId;

    @DatabaseField(columnName = "shgAge")
    @SerializedName("shgAge")
    private String shgAge;

    @DatabaseField(columnName = "shgPhoneNumber")
    @SerializedName("shgPhoneNumber")
    private String shgPhoneNumber;

    @DatabaseField(columnName = "shgAmountToBeSaved")
    @SerializedName("shgAmountToBeSaved")
    private String shgAmountToBeSaved;

    @DatabaseField(columnName = "shgInterestRate")
    @SerializedName("shgInterestRate")
    private String shgInterestRate;

    @DatabaseField(columnName = "shgAssociatedBank")
    @SerializedName("shgAssociatedBank")
    private String shgAssociatedBank;

    @DatabaseField(columnName = "shgAccountNumber")
    @SerializedName("shgAccountNumber")
    private String shgAccountNumber;

    @DatabaseField(columnName = "shgCreate")
    @SerializedName("shgCreate")
    private String shgCreate;

    @DatabaseField(columnName = "shgUpdate")
    @SerializedName("shgUpdate")
    private String shgUpdate;

    @DatabaseField(columnName = "hhModule")
    @SerializedName("hhModule")
    private String hhModule;

    @DatabaseField(columnName = "hhFullName")
    @SerializedName("hhFullName")
    private String hhFullName;

    @DatabaseField(columnName = "hhHHId")
    @SerializedName("hhHHId")
    private String hhHHId;

    @DatabaseField(columnName = "hhIndividualId")
    @SerializedName("hhIndividualId")
    private String hhIndividualId;

    @DatabaseField(columnName = "hhEsiNo")
    @SerializedName("hhEsiNo")
    private String hhEsiNo;

    @DatabaseField(columnName = "hhAadharNo")
    @SerializedName("hhAadharNo")
    private String hhAadharNo;

    @DatabaseField(columnName = "hhDOB")
    @SerializedName("hhDOB")
    private String hhDOB;

    @DatabaseField(columnName = "hhAge")
    @SerializedName("hhAge")
    private String hhAge;

    @DatabaseField(columnName = "hhGender")
    @SerializedName("hhGender")
    private String hhGender;

    @DatabaseField(columnName = "hhPhoneNumber")
    @SerializedName("hhPhoneNumber")
    private String hhPhoneNumber;

    @DatabaseField(columnName = "hhOccupation")
    @SerializedName("hhOccupation")
    private String hhOccupation;

    @DatabaseField(columnName = "hhSHGBelongs")
    @SerializedName("hhSHGBelongs")
    private String hhSHGBelongs;

    @DatabaseField(columnName = "hhAddress")
    @SerializedName("hhAddress")
    private String hhAddress;

    @DatabaseField(columnName = "hhEmail")
    @SerializedName("hhEmail")
    private String hhEmail;

    @DatabaseField(columnName = "hhCaste")
    @SerializedName("hhCaste")
    private String hhCaste;

    @DatabaseField(columnName = "hhReligion")
    @SerializedName("hhReligion")
    private String hhReligion;

    @DatabaseField(columnName = "hhMigrant")
    @SerializedName("hhMigrant")
    private String hhMigrant;

    @DatabaseField(columnName = "hhBPLStatus")
    @SerializedName("hhBPLStatus")
    private String hhBPLStatus;

    @DatabaseField(columnName = "hhLatitude")
    @SerializedName("hhLatitude")
    private String hhLatitude;

    @DatabaseField(columnName = "hhLongitude")
    @SerializedName("hhLongitude")
    private String hhLongitude;

    @DatabaseField(columnName = "hhEdit")
    @SerializedName("hhEdit")
    private String hhEdit;

    @DatabaseField(columnName = "hhDelete")
    @SerializedName("hhDelete")
    private String hhDelete;

    @DatabaseField(columnName = "hhArchive")
    @SerializedName("hhArchive")
    private String hhArchive;

    @DatabaseField(columnName = "hhAdd")
    @SerializedName("hhAdd")
    private String hhAdd;

    @DatabaseField(columnName = "hhLocateMe")
    @SerializedName("hhLocateMe")
    private String hhLocateMe;

    @DatabaseField(columnName = "hhName")
    @SerializedName("hhName")
    private String hhName;

    @DatabaseField(columnName = "hhRelation")
    @SerializedName("hhRelation")
    private String hhRelation;

    @DatabaseField(columnName = "hhDOBHH")
    @SerializedName("hhDOBHH")
    private String hhDOBHH;

    @DatabaseField(columnName = "hhAgeHH")
    @SerializedName("hhAgeHH")
    private String hhAgeHH;

    @DatabaseField(columnName = "hhLiteracyLevel")
    @SerializedName("hhLiteracyLevel")
    private String hhLiteracyLevel;

    @DatabaseField(columnName = "hhNameOfSchool")
    @SerializedName("hhNameOfSchool")
    private String hhNameOfSchool;

    @DatabaseField(columnName = "hhNameOfFactory")
    @SerializedName("hhNameOfFactory")
    private String hhNameOfFactory;

    @DatabaseField(columnName = "hhAadharNoHH")
    @SerializedName("hhAadharNoHH")
    private String hhAadharNoHH;

    @DatabaseField(columnName = "hhEsiNoHH")
    @SerializedName("hhEsiNoHH")
    private String hhEsiNoHH;

    @DatabaseField(columnName = "hhSHGName")
    @SerializedName("hhSHGName")
    private String hhSHGName;

    @DatabaseField(columnName = "hhGenderHH")
    @SerializedName("hhGenderHH")
    private String hhGenderHH;

    @DatabaseField(columnName = "hhIndividualIdHH")
    @SerializedName("hhIndividualIdHH")
    private String hhIndividualIdHH;

    @DatabaseField(columnName = "hhCreate")
    @SerializedName("hhCreate")
    private String hhCreate;

    @DatabaseField(columnName = "hhUpdate")
    @SerializedName("hhUpdate")
    private String hhUpdate;

    @DatabaseField(columnName = "afsModule")
    @SerializedName("afsModule")
    private String afsModule;

    @DatabaseField(columnName = "afsMember")
    @SerializedName("afsMember")
    private String afsMember;

    @DatabaseField(columnName = "afsScheme")
    @SerializedName("afsScheme")
    private String afsScheme;

    @DatabaseField(columnName = "afsStage")
    @SerializedName("afsStage")
    private String afsStage;

    @DatabaseField(columnName = "afsResponseReceived")
    @SerializedName("afsResponseReceived")
    private String afsResponseReceived;

    @DatabaseField(columnName = "afsReasonForRejection")
    @SerializedName("afsReasonForRejection")
    private String afsReasonForRejection;

    @DatabaseField(columnName = "afsReasonForDelay")
    @SerializedName("afsReasonForDelay")
    private String afsReasonForDelay;

    @DatabaseField(columnName = "afsFollowUpDate")
    @SerializedName("afsFollowUpDate")
    private String afsFollowUpDate;

    @DatabaseField(columnName = "afsApply")
    @SerializedName("afsApply")
    private String afsApply;

    @DatabaseField(columnName = "afsUpdate")
    @SerializedName("afsUpdate")
    private String afsUpdate;

    @DatabaseField(columnName = "adsModule")
    @SerializedName("adsModule")
    private String adsModule;

    @DatabaseField(columnName = "adsMember")
    @SerializedName("adsMember")
    private String adsMember;

    @DatabaseField(columnName = "adsDocument")
    @SerializedName("adsDocument")
    private String adsDocument;

    @DatabaseField(columnName = "adsStage")
    @SerializedName("adsStage")
    private String adsStage;

    @DatabaseField(columnName = "adsResponseReceived")
    @SerializedName("adsResponseReceived")
    private String adsResponseReceived;

    @DatabaseField(columnName = "adsReasonForRejection")
    @SerializedName("adsReasonForRejection")
    private String adsReasonForRejection;

    @DatabaseField(columnName = "adsReasonForDelay")
    @SerializedName("adsReasonForDelay")
    private String adsReasonForDelay;

    @DatabaseField(columnName = "adsFollowUpDate")
    @SerializedName("adsFollowUpDate")
    private String adsFollowUpDate;

    @DatabaseField(columnName = "adsApply")
    @SerializedName("adsApply")
    private String adsApply;

    @DatabaseField(columnName = "adsUpdate")
    @SerializedName("adsUpdate")
    private String adsUpdate;

    @DatabaseField(columnName = "riModule")
    @SerializedName("riModule")
    private String riModule;

    @DatabaseField(columnName = "riWhenHappened")
    @SerializedName("riWhenHappened")
    private String riWhenHappened;

    @DatabaseField(columnName = "riTypeOfIncident")
    @SerializedName("riTypeOfIncident")
    private String riTypeOfIncident;

    @DatabaseField(columnName = "riMultipleMembers")
    @SerializedName("riMultipleMembers")
    private String riMultipleMembers;

    @DatabaseField(columnName = "riMembers")
    @SerializedName("riMembers")
    private String riMembers;

    @DatabaseField(columnName = "riPhysical")
    @SerializedName("riPhysical")
    private String riPhysical;

    @DatabaseField(columnName = "riEmotional")
    @SerializedName("riEmotional")
    private String riEmotional;

    @DatabaseField(columnName = "riSexual")
    @SerializedName("riSexual")
    private String riSexual;

    @DatabaseField(columnName = "riMental")
    @SerializedName("riMental")
    private String riMental;

    @DatabaseField(columnName = "riPropertyRelated")
    @SerializedName("riPropertyRelated")
    private String riPropertyRelated;

    @DatabaseField(columnName = "riChildRelated")
    @SerializedName("riChildRelated")
    private String riChildRelated;

    @DatabaseField(columnName = "riWound")
    @SerializedName("riWound")
    private String riWound;

    @DatabaseField(columnName = "riCut")
    @SerializedName("riCut")
    private String riCut;

    @DatabaseField(columnName = "riSeverePain")
    @SerializedName("riSeverePain")
    private String riSeverePain;

    @DatabaseField(columnName = "riBleeding")
    @SerializedName("riBleeding")
    private String riBleeding;

    @DatabaseField(columnName = "riImMobile")
    @SerializedName("riImMobile")
    private String riImMobile;

    @DatabaseField(columnName = "riPassingOut")
    @SerializedName("riPassingOut")
    private String riPassingOut;

    @DatabaseField(columnName = "riUncoveredFood")
    @SerializedName("riUncoveredFood")
    private String riUncoveredFood;

    @DatabaseField(columnName = "riUnclearedGarbage")
    @SerializedName("riUnclearedGarbage")
    private String riUnclearedGarbage;

    @DatabaseField(columnName = "riWaterStagnation")
    @SerializedName("riWaterStagnation")
    private String riWaterStagnation;

    @DatabaseField(columnName = "riSeverityOfIncident")
    @SerializedName("riSeverityOfIncident")
    private String riSeverityOfIncident;

    @DatabaseField(columnName = "riPolice")
    @SerializedName("riPolice")
    private String riPolice;

    @DatabaseField(columnName = "riGoons")
    @SerializedName("riGoons")
    private String riGoons;

    @DatabaseField(columnName = "riFatherInLaw")
    @SerializedName("riFatherInLaw")
    private String riFatherInLaw;

    @DatabaseField(columnName = "riMotherInLaw")
    @SerializedName("riMotherInLaw")
    private String riMotherInLaw;

    @DatabaseField(columnName = "riPartner")
    @SerializedName("riPartner")
    private String riPartner;

    @DatabaseField(columnName = "riHusband")
    @SerializedName("riHusband")
    private String riHusband;

    @DatabaseField(columnName = "riSisterOrBrotherInLaw")
    @SerializedName("riSisterOrBrotherInLaw")
    private String riSisterOrBrotherInLaw;

    @DatabaseField(columnName = "riAnyOtherMember")
    @SerializedName("riAnyOtherMember")
    private String riAnyOtherMember;

    @DatabaseField(columnName = "riSonOrDaughter")
    @SerializedName("riSonOrDaughter")
    private String riSonOrDaughter;

    @DatabaseField(columnName = "riDaughterInLaw")
    @SerializedName("riDaughterInLaw")
    private String riDaughterInLaw;

    @DatabaseField(columnName = "riOthers")
    @SerializedName("riOthers")
    private String riOthers;

    @DatabaseField(columnName = "riLandLords")
    @SerializedName("riLandLords")
    private String riLandLords;

    @DatabaseField(columnName = "riHusbandFriends")
    @SerializedName("riHusbandFriends")
    private String riHusbandFriends;

    @DatabaseField(columnName = "riUnmarriedPerson")
    @SerializedName("riUnmarriedPerson")
    private String riUnmarriedPerson;

    @DatabaseField(columnName = "riReported")
    @SerializedName("riReported")
    private String riReported;

    @DatabaseField(columnName = "riReportedTo")
    @SerializedName("riReportedTo")
    private String riReportedTo;

    @DatabaseField(columnName = "riReportedToPolice")
    @SerializedName("riReportedToPolice")
    private String riReportedToPolice;

    @DatabaseField(columnName = "riReportedToNGO")
    @SerializedName("riReportedToNGO")
    private String riReportedToNGO;

    @DatabaseField(columnName = "riReportedToFriends")
    @SerializedName("riReportedToFriends")
    private String riReportedToFriends;

    @DatabaseField(columnName = "riReportedToPLV")
    @SerializedName("riReportedToPLV")
    private String riReportedToPLV;

    @DatabaseField(columnName = "riReportedToSHG")
    @SerializedName("riReportedToSHG")
    private String riReportedToSHG;

    @DatabaseField(columnName = "riReportedToHF")
    @SerializedName("riReportedToHF")
    private String riReportedToHF;

    @DatabaseField(columnName = "riReportedToChampions")
    @SerializedName("riReportedToChampions")
    private String riReportedToChampions;

    @DatabaseField(columnName = "riReportedToDoctorOrNurse")
    @SerializedName("riReportedToDoctorOrNurse")
    private String riReportedToDoctorOrNurse;

    @DatabaseField(columnName = "riReportedToLegalAidClinic")
    @SerializedName("riReportedToLegalAidClinic")
    private String riReportedToLegalAidClinic;

    @DatabaseField(columnName = "riReportedToGramPanchayat")
    @SerializedName("riReportedToGramPanchayat")
    private String riReportedToGramPanchayat;

    @DatabaseField(columnName = "riTimeToRespond")
    @SerializedName("riTimeToRespond")
    private String riTimeToRespond;

    @DatabaseField(columnName = "riReferredToHF")
    @SerializedName("riReferredToHF")
    private String riReferredToHF;

    @DatabaseField(columnName = "riReferredForMedical")
    @SerializedName("riReferredForMedical")
    private String riReferredForMedical;

    @DatabaseField(columnName = "riReferredToCOManager")
    @SerializedName("riReferredToCOManager")
    private String riReferredToCOManager;

    @DatabaseField(columnName = "riReferredToheadOfGP")
    @SerializedName("riReferredToheadOfGP")
    private String riReferredToheadOfGP;

    @DatabaseField(columnName = "riReferredToPLV")
    @SerializedName("riReferredToPLV")
    private String riReferredToPLV;

    @DatabaseField(columnName = "riReferredToLegalAidClinic")
    @SerializedName("riReferredToLegalAidClinic")
    private String riReferredToLegalAidClinic;

    @DatabaseField(columnName = "riReferredToPolice")
    @SerializedName("riReferredToPolice")
    private String riReferredToPolice;

    @DatabaseField(columnName = "riReferredToSHG")
    @SerializedName("riReferredToSHG")
    private String riReferredToSHG;

    @DatabaseField(columnName = "riEmergencyActionTaken")
    @SerializedName("riEmergencyActionTaken")
    private String riEmergencyActionTaken;

    @DatabaseField(columnName = "riReferredToHospital")
    @SerializedName("riReferredToHospital")
    private String riReferredToHospital;

    @DatabaseField(columnName = "riComplaintMadeInSW")
    @SerializedName("riComplaintMadeInSW")
    private String riComplaintMadeInSW;

    @DatabaseField(columnName = "riCommunityMobilized")
    @SerializedName("riCommunityMobilized")
    private String riCommunityMobilized;

    @DatabaseField(columnName = "riPrioritizedSAWF")
    @SerializedName("riPrioritizedSAWF")
    private String riPrioritizedSAWF;

    @DatabaseField(columnName = "riCurrentStatus")
    @SerializedName("riCurrentStatus")
    private String riCurrentStatus;

    @DatabaseField(columnName = "riFollowUpNeeded")
    @SerializedName("riFollowUpNeeded")
    private String riFollowUpNeeded;

    @DatabaseField(columnName = "riFollowUpDate")
    @SerializedName("riFollowUpDate")
    private String riFollowUpDate;

    @DatabaseField(columnName = "riClosureDate")
    @SerializedName("riClosureDate")
    private String riClosureDate;

    @DatabaseField(columnName = "riCreate")
    @SerializedName("riCreate")
    private String riCreate;

    @DatabaseField(columnName = "riUpdate")
    @SerializedName("riUpdate")
    private String riUpdate;

    @DatabaseField(columnName = "createdBy")
    @SerializedName("createdBy")
    private String createdBy;

    @DatabaseField(columnName = "createdDate")
    @SerializedName("createdDate")
    private String createdDate;

    @DatabaseField(columnName = "createdByRole")
    @SerializedName("createdByRole")
    private String createdByRole;

    @DatabaseField(columnName = "lastModifiedBy")
    @SerializedName("lastModifiedBy")
    private String lastModifiedBy;

    @DatabaseField(columnName = "lastModifiedDate")
    @SerializedName("lastModifiedDate")
    private String lastModifiedDate;

    @DatabaseField(columnName = "lastModifiedByRole")
    @SerializedName("lastModifiedByRole")
    private String lastModifiedByRole;

    @DatabaseField(columnName = "deleteFlag")
    @SerializedName("deleteFlag")
    private String deleteFlag;

    @DatabaseField(columnName = "id")
    @SerializedName("id")
    private Integer id;

    @DatabaseField(columnName = "name")
    @SerializedName("name")
    private String name;

    @DatabaseField(columnName = "description")
    @SerializedName("description")
    private String description;

    @DatabaseField(columnName = "created")
    @SerializedName("created")
    private String created;

    @DatabaseField(columnName = "modified")
    @SerializedName("modified")
    private String modified;

    public String getShgModule() {
        return shgModule;
    }

    public void setShgModule(String shgModule) {
        this.shgModule = shgModule;
    }

    public String getShgGroupName() {
        return shgGroupName;
    }

    public void setShgGroupName(String shgGroupName) {
        this.shgGroupName = shgGroupName;
    }

    public String getShgFormationDate() {
        return shgFormationDate;
    }

    public void setShgFormationDate(String shgFormationDate) {
        this.shgFormationDate = shgFormationDate;
    }

    public String getShgAssociatedHF() {
        return shgAssociatedHF;
    }

    public void setShgAssociatedHF(String shgAssociatedHF) {
        this.shgAssociatedHF = shgAssociatedHF;
    }

    public String getShgMeetingInterval() {
        return shgMeetingInterval;
    }

    public void setShgMeetingInterval(String shgMeetingInterval) {
        this.shgMeetingInterval = shgMeetingInterval;
    }

    public String getShgSearchForName() {
        return shgSearchForName;
    }

    public void setShgSearchForName(String shgSearchForName) {
        this.shgSearchForName = shgSearchForName;
    }

    public String getShgName() {
        return shgName;
    }

    public void setShgName(String shgName) {
        this.shgName = shgName;
    }

    public String getShgAddMember() {
        return shgAddMember;
    }

    public void setShgAddMember(String shgAddMember) {
        this.shgAddMember = shgAddMember;
    }

    public String getShgIndividualId() {
        return shgIndividualId;
    }

    public void setShgIndividualId(String shgIndividualId) {
        this.shgIndividualId = shgIndividualId;
    }

    public String getShgAge() {
        return shgAge;
    }

    public void setShgAge(String shgAge) {
        this.shgAge = shgAge;
    }

    public String getShgPhoneNumber() {
        return shgPhoneNumber;
    }

    public void setShgPhoneNumber(String shgPhoneNumber) {
        this.shgPhoneNumber = shgPhoneNumber;
    }

    public String getShgAmountToBeSaved() {
        return shgAmountToBeSaved;
    }

    public void setShgAmountToBeSaved(String shgAmountToBeSaved) {
        this.shgAmountToBeSaved = shgAmountToBeSaved;
    }

    public String getShgInterestRate() {
        return shgInterestRate;
    }

    public void setShgInterestRate(String shgInterestRate) {
        this.shgInterestRate = shgInterestRate;
    }

    public String getShgAssociatedBank() {
        return shgAssociatedBank;
    }

    public void setShgAssociatedBank(String shgAssociatedBank) {
        this.shgAssociatedBank = shgAssociatedBank;
    }

    public String getShgAccountNumber() {
        return shgAccountNumber;
    }

    public void setShgAccountNumber(String shgAccountNumber) {
        this.shgAccountNumber = shgAccountNumber;
    }

    public String getShgCreate() {
        return shgCreate;
    }

    public void setShgCreate(String shgCreate) {
        this.shgCreate = shgCreate;
    }

    public String getShgUpdate() {
        return shgUpdate;
    }

    public void setShgUpdate(String shgUpdate) {
        this.shgUpdate = shgUpdate;
    }

    public String getHhModule() {
        return hhModule;
    }

    public void setHhModule(String hhModule) {
        this.hhModule = hhModule;
    }

    public String getHhFullName() {
        return hhFullName;
    }

    public void setHhFullName(String hhFullName) {
        this.hhFullName = hhFullName;
    }

    public String getHhHHId() {
        return hhHHId;
    }

    public void setHhHHId(String hhHHId) {
        this.hhHHId = hhHHId;
    }

    public String getHhIndividualId() {
        return hhIndividualId;
    }

    public void setHhIndividualId(String hhIndividualId) {
        this.hhIndividualId = hhIndividualId;
    }

    public String getHhEsiNo() {
        return hhEsiNo;
    }

    public void setHhEsiNo(String hhEsiNo) {
        this.hhEsiNo = hhEsiNo;
    }

    public String getHhAadharNo() {
        return hhAadharNo;
    }

    public void setHhAadharNo(String hhAadharNo) {
        this.hhAadharNo = hhAadharNo;
    }

    public String getHhDOB() {
        return hhDOB;
    }

    public void setHhDOB(String hhDOB) {
        this.hhDOB = hhDOB;
    }

    public String getHhAge() {
        return hhAge;
    }

    public void setHhAge(String hhAge) {
        this.hhAge = hhAge;
    }

    public String getHhGender() {
        return hhGender;
    }

    public void setHhGender(String hhGender) {
        this.hhGender = hhGender;
    }

    public String getHhPhoneNumber() {
        return hhPhoneNumber;
    }

    public void setHhPhoneNumber(String hhPhoneNumber) {
        this.hhPhoneNumber = hhPhoneNumber;
    }

    public String getHhOccupation() {
        return hhOccupation;
    }

    public void setHhOccupation(String hhOccupation) {
        this.hhOccupation = hhOccupation;
    }

    public String getHhSHGBelongs() {
        return hhSHGBelongs;
    }

    public void setHhSHGBelongs(String hhSHGBelongs) {
        this.hhSHGBelongs = hhSHGBelongs;
    }

    public String getHhAddress() {
        return hhAddress;
    }

    public void setHhAddress(String hhAddress) {
        this.hhAddress = hhAddress;
    }

    public String getHhEmail() {
        return hhEmail;
    }

    public void setHhEmail(String hhEmail) {
        this.hhEmail = hhEmail;
    }

    public String getHhCaste() {
        return hhCaste;
    }

    public void setHhCaste(String hhCaste) {
        this.hhCaste = hhCaste;
    }

    public String getHhReligion() {
        return hhReligion;
    }

    public void setHhReligion(String hhReligion) {
        this.hhReligion = hhReligion;
    }

    public String getHhMigrant() {
        return hhMigrant;
    }

    public void setHhMigrant(String hhMigrant) {
        this.hhMigrant = hhMigrant;
    }

    public String getHhBPLStatus() {
        return hhBPLStatus;
    }

    public void setHhBPLStatus(String hhBPLStatus) {
        this.hhBPLStatus = hhBPLStatus;
    }

    public String getHhLatitude() {
        return hhLatitude;
    }

    public void setHhLatitude(String hhLatitude) {
        this.hhLatitude = hhLatitude;
    }

    public String getHhLongitude() {
        return hhLongitude;
    }

    public void setHhLongitude(String hhLongitude) {
        this.hhLongitude = hhLongitude;
    }

    public String getHhEdit() {
        return hhEdit;
    }

    public void setHhEdit(String hhEdit) {
        this.hhEdit = hhEdit;
    }

    public String getHhDelete() {
        return hhDelete;
    }

    public void setHhDelete(String hhDelete) {
        this.hhDelete = hhDelete;
    }

    public String getHhArchive() {
        return hhArchive;
    }

    public void setHhArchive(String hhArchive) {
        this.hhArchive = hhArchive;
    }

    public String getHhAdd() {
        return hhAdd;
    }

    public void setHhAdd(String hhAdd) {
        this.hhAdd = hhAdd;
    }

    public String getHhLocateMe() {
        return hhLocateMe;
    }

    public void setHhLocateMe(String hhLocateMe) {
        this.hhLocateMe = hhLocateMe;
    }

    public String getHhName() {
        return hhName;
    }

    public void setHhName(String hhName) {
        this.hhName = hhName;
    }

    public String getHhRelation() {
        return hhRelation;
    }

    public void setHhRelation(String hhRelation) {
        this.hhRelation = hhRelation;
    }

    public String getHhDOBHH() {
        return hhDOBHH;
    }

    public void setHhDOBHH(String hhDOBHH) {
        this.hhDOBHH = hhDOBHH;
    }

    public String getHhAgeHH() {
        return hhAgeHH;
    }

    public void setHhAgeHH(String hhAgeHH) {
        this.hhAgeHH = hhAgeHH;
    }

    public String getHhLiteracyLevel() {
        return hhLiteracyLevel;
    }

    public void setHhLiteracyLevel(String hhLiteracyLevel) {
        this.hhLiteracyLevel = hhLiteracyLevel;
    }

    public String getHhNameOfSchool() {
        return hhNameOfSchool;
    }

    public void setHhNameOfSchool(String hhNameOfSchool) {
        this.hhNameOfSchool = hhNameOfSchool;
    }

    public String getHhNameOfFactory() {
        return hhNameOfFactory;
    }

    public void setHhNameOfFactory(String hhNameOfFactory) {
        this.hhNameOfFactory = hhNameOfFactory;
    }

    public String getHhAadharNoHH() {
        return hhAadharNoHH;
    }

    public void setHhAadharNoHH(String hhAadharNoHH) {
        this.hhAadharNoHH = hhAadharNoHH;
    }

    public String getHhEsiNoHH() {
        return hhEsiNoHH;
    }

    public void setHhEsiNoHH(String hhEsiNoHH) {
        this.hhEsiNoHH = hhEsiNoHH;
    }

    public String getHhSHGName() {
        return hhSHGName;
    }

    public void setHhSHGName(String hhSHGName) {
        this.hhSHGName = hhSHGName;
    }

    public String getHhGenderHH() {
        return hhGenderHH;
    }

    public void setHhGenderHH(String hhGenderHH) {
        this.hhGenderHH = hhGenderHH;
    }

    public String getHhIndividualIdHH() {
        return hhIndividualIdHH;
    }

    public void setHhIndividualIdHH(String hhIndividualIdHH) {
        this.hhIndividualIdHH = hhIndividualIdHH;
    }

    public String getHhCreate() {
        return hhCreate;
    }

    public void setHhCreate(String hhCreate) {
        this.hhCreate = hhCreate;
    }

    public String getHhUpdate() {
        return hhUpdate;
    }

    public void setHhUpdate(String hhUpdate) {
        this.hhUpdate = hhUpdate;
    }

    public String getAfsModule() {
        return afsModule;
    }

    public void setAfsModule(String afsModule) {
        this.afsModule = afsModule;
    }

    public String getAfsMember() {
        return afsMember;
    }

    public void setAfsMember(String afsMember) {
        this.afsMember = afsMember;
    }

    public String getAfsScheme() {
        return afsScheme;
    }

    public void setAfsScheme(String afsScheme) {
        this.afsScheme = afsScheme;
    }

    public String getAfsStage() {
        return afsStage;
    }

    public void setAfsStage(String afsStage) {
        this.afsStage = afsStage;
    }

    public String getAfsResponseReceived() {
        return afsResponseReceived;
    }

    public void setAfsResponseReceived(String afsResponseReceived) {
        this.afsResponseReceived = afsResponseReceived;
    }

    public String getAfsReasonForRejection() {
        return afsReasonForRejection;
    }

    public void setAfsReasonForRejection(String afsReasonForRejection) {
        this.afsReasonForRejection = afsReasonForRejection;
    }

    public String getAfsReasonForDelay() {
        return afsReasonForDelay;
    }

    public void setAfsReasonForDelay(String afsReasonForDelay) {
        this.afsReasonForDelay = afsReasonForDelay;
    }

    public String getAfsFollowUpDate() {
        return afsFollowUpDate;
    }

    public void setAfsFollowUpDate(String afsFollowUpDate) {
        this.afsFollowUpDate = afsFollowUpDate;
    }

    public String getAfsApply() {
        return afsApply;
    }

    public void setAfsApply(String afsApply) {
        this.afsApply = afsApply;
    }

    public String getAfsUpdate() {
        return afsUpdate;
    }

    public void setAfsUpdate(String afsUpdate) {
        this.afsUpdate = afsUpdate;
    }

    public String getAdsModule() {
        return adsModule;
    }

    public void setAdsModule(String adsModule) {
        this.adsModule = adsModule;
    }

    public String getAdsMember() {
        return adsMember;
    }

    public void setAdsMember(String adsMember) {
        this.adsMember = adsMember;
    }

    public String getAdsDocument() {
        return adsDocument;
    }

    public void setAdsDocument(String adsDocument) {
        this.adsDocument = adsDocument;
    }

    public String getAdsStage() {
        return adsStage;
    }

    public void setAdsStage(String adsStage) {
        this.adsStage = adsStage;
    }

    public String getAdsResponseReceived() {
        return adsResponseReceived;
    }

    public void setAdsResponseReceived(String adsResponseReceived) {
        this.adsResponseReceived = adsResponseReceived;
    }

    public String getAdsReasonForRejection() {
        return adsReasonForRejection;
    }

    public void setAdsReasonForRejection(String adsReasonForRejection) {
        this.adsReasonForRejection = adsReasonForRejection;
    }

    public String getAdsReasonForDelay() {
        return adsReasonForDelay;
    }

    public void setAdsReasonForDelay(String adsReasonForDelay) {
        this.adsReasonForDelay = adsReasonForDelay;
    }

    public String getAdsFollowUpDate() {
        return adsFollowUpDate;
    }

    public void setAdsFollowUpDate(String adsFollowUpDate) {
        this.adsFollowUpDate = adsFollowUpDate;
    }

    public String getAdsApply() {
        return adsApply;
    }

    public void setAdsApply(String adsApply) {
        this.adsApply = adsApply;
    }

    public String getAdsUpdate() {
        return adsUpdate;
    }

    public void setAdsUpdate(String adsUpdate) {
        this.adsUpdate = adsUpdate;
    }

    public String getRiModule() {
        return riModule;
    }

    public void setRiModule(String riModule) {
        this.riModule = riModule;
    }

    public String getRiWhenHappened() {
        return riWhenHappened;
    }

    public void setRiWhenHappened(String riWhenHappened) {
        this.riWhenHappened = riWhenHappened;
    }

    public String getRiTypeOfIncident() {
        return riTypeOfIncident;
    }

    public void setRiTypeOfIncident(String riTypeOfIncident) {
        this.riTypeOfIncident = riTypeOfIncident;
    }

    public String getRiMultipleMembers() {
        return riMultipleMembers;
    }

    public void setRiMultipleMembers(String riMultipleMembers) {
        this.riMultipleMembers = riMultipleMembers;
    }

    public String getRiMembers() {
        return riMembers;
    }

    public void setRiMembers(String riMembers) {
        this.riMembers = riMembers;
    }

    public String getRiPhysical() {
        return riPhysical;
    }

    public void setRiPhysical(String riPhysical) {
        this.riPhysical = riPhysical;
    }

    public String getRiEmotional() {
        return riEmotional;
    }

    public void setRiEmotional(String riEmotional) {
        this.riEmotional = riEmotional;
    }

    public String getRiSexual() {
        return riSexual;
    }

    public void setRiSexual(String riSexual) {
        this.riSexual = riSexual;
    }

    public String getRiMental() {
        return riMental;
    }

    public void setRiMental(String riMental) {
        this.riMental = riMental;
    }

    public String getRiPropertyRelated() {
        return riPropertyRelated;
    }

    public void setRiPropertyRelated(String riPropertyRelated) {
        this.riPropertyRelated = riPropertyRelated;
    }

    public String getRiChildRelated() {
        return riChildRelated;
    }

    public void setRiChildRelated(String riChildRelated) {
        this.riChildRelated = riChildRelated;
    }

    public String getRiWound() {
        return riWound;
    }

    public void setRiWound(String riWound) {
        this.riWound = riWound;
    }

    public String getRiCut() {
        return riCut;
    }

    public void setRiCut(String riCut) {
        this.riCut = riCut;
    }

    public String getRiSeverePain() {
        return riSeverePain;
    }

    public void setRiSeverePain(String riSeverePain) {
        this.riSeverePain = riSeverePain;
    }

    public String getRiBleeding() {
        return riBleeding;
    }

    public void setRiBleeding(String riBleeding) {
        this.riBleeding = riBleeding;
    }

    public String getRiImMobile() {
        return riImMobile;
    }

    public void setRiImMobile(String riImMobile) {
        this.riImMobile = riImMobile;
    }

    public String getRiPassingOut() {
        return riPassingOut;
    }

    public void setRiPassingOut(String riPassingOut) {
        this.riPassingOut = riPassingOut;
    }

    public String getRiUncoveredFood() {
        return riUncoveredFood;
    }

    public void setRiUncoveredFood(String riUncoveredFood) {
        this.riUncoveredFood = riUncoveredFood;
    }

    public String getRiUnclearedGarbage() {
        return riUnclearedGarbage;
    }

    public void setRiUnclearedGarbage(String riUnclearedGarbage) {
        this.riUnclearedGarbage = riUnclearedGarbage;
    }

    public String getRiWaterStagnation() {
        return riWaterStagnation;
    }

    public void setRiWaterStagnation(String riWaterStagnation) {
        this.riWaterStagnation = riWaterStagnation;
    }

    public String getRiSeverityOfIncident() {
        return riSeverityOfIncident;
    }

    public void setRiSeverityOfIncident(String riSeverityOfIncident) {
        this.riSeverityOfIncident = riSeverityOfIncident;
    }

    public String getRiPolice() {
        return riPolice;
    }

    public void setRiPolice(String riPolice) {
        this.riPolice = riPolice;
    }

    public String getRiGoons() {
        return riGoons;
    }

    public void setRiGoons(String riGoons) {
        this.riGoons = riGoons;
    }

    public String getRiFatherInLaw() {
        return riFatherInLaw;
    }

    public void setRiFatherInLaw(String riFatherInLaw) {
        this.riFatherInLaw = riFatherInLaw;
    }

    public String getRiMotherInLaw() {
        return riMotherInLaw;
    }

    public void setRiMotherInLaw(String riMotherInLaw) {
        this.riMotherInLaw = riMotherInLaw;
    }

    public String getRiPartner() {
        return riPartner;
    }

    public void setRiPartner(String riPartner) {
        this.riPartner = riPartner;
    }

    public String getRiHusband() {
        return riHusband;
    }

    public void setRiHusband(String riHusband) {
        this.riHusband = riHusband;
    }

    public String getRiSisterOrBrotherInLaw() {
        return riSisterOrBrotherInLaw;
    }

    public void setRiSisterOrBrotherInLaw(String riSisterOrBrotherInLaw) {
        this.riSisterOrBrotherInLaw = riSisterOrBrotherInLaw;
    }

    public String getRiAnyOtherMember() {
        return riAnyOtherMember;
    }

    public void setRiAnyOtherMember(String riAnyOtherMember) {
        this.riAnyOtherMember = riAnyOtherMember;
    }

    public String getRiSonOrDaughter() {
        return riSonOrDaughter;
    }

    public void setRiSonOrDaughter(String riSonOrDaughter) {
        this.riSonOrDaughter = riSonOrDaughter;
    }

    public String getRiDaughterInLaw() {
        return riDaughterInLaw;
    }

    public void setRiDaughterInLaw(String riDaughterInLaw) {
        this.riDaughterInLaw = riDaughterInLaw;
    }

    public String getRiOthers() {
        return riOthers;
    }

    public void setRiOthers(String riOthers) {
        this.riOthers = riOthers;
    }

    public String getRiLandLords() {
        return riLandLords;
    }

    public void setRiLandLords(String riLandLords) {
        this.riLandLords = riLandLords;
    }

    public String getRiHusbandFriends() {
        return riHusbandFriends;
    }

    public void setRiHusbandFriends(String riHusbandFriends) {
        this.riHusbandFriends = riHusbandFriends;
    }

    public String getRiUnmarriedPerson() {
        return riUnmarriedPerson;
    }

    public void setRiUnmarriedPerson(String riUnmarriedPerson) {
        this.riUnmarriedPerson = riUnmarriedPerson;
    }

    public String getRiReported() {
        return riReported;
    }

    public void setRiReported(String riReported) {
        this.riReported = riReported;
    }

    public String getRiReportedTo() {
        return riReportedTo;
    }

    public void setRiReportedTo(String riReportedTo) {
        this.riReportedTo = riReportedTo;
    }

    public String getRiReportedToPolice() {
        return riReportedToPolice;
    }

    public void setRiReportedToPolice(String riReportedToPolice) {
        this.riReportedToPolice = riReportedToPolice;
    }

    public String getRiReportedToNGO() {
        return riReportedToNGO;
    }

    public void setRiReportedToNGO(String riReportedToNGO) {
        this.riReportedToNGO = riReportedToNGO;
    }

    public String getRiReportedToFriends() {
        return riReportedToFriends;
    }

    public void setRiReportedToFriends(String riReportedToFriends) {
        this.riReportedToFriends = riReportedToFriends;
    }

    public String getRiReportedToPLV() {
        return riReportedToPLV;
    }

    public void setRiReportedToPLV(String riReportedToPLV) {
        this.riReportedToPLV = riReportedToPLV;
    }

    public String getRiReportedToSHG() {
        return riReportedToSHG;
    }

    public void setRiReportedToSHG(String riReportedToSHG) {
        this.riReportedToSHG = riReportedToSHG;
    }

    public String getRiReportedToHF() {
        return riReportedToHF;
    }

    public void setRiReportedToHF(String riReportedToHF) {
        this.riReportedToHF = riReportedToHF;
    }

    public String getRiReportedToChampions() {
        return riReportedToChampions;
    }

    public void setRiReportedToChampions(String riReportedToChampions) {
        this.riReportedToChampions = riReportedToChampions;
    }

    public String getRiReportedToDoctorOrNurse() {
        return riReportedToDoctorOrNurse;
    }

    public void setRiReportedToDoctorOrNurse(String riReportedToDoctorOrNurse) {
        this.riReportedToDoctorOrNurse = riReportedToDoctorOrNurse;
    }

    public String getRiReportedToLegalAidClinic() {
        return riReportedToLegalAidClinic;
    }

    public void setRiReportedToLegalAidClinic(String riReportedToLegalAidClinic) {
        this.riReportedToLegalAidClinic = riReportedToLegalAidClinic;
    }

    public String getRiReportedToGramPanchayat() {
        return riReportedToGramPanchayat;
    }

    public void setRiReportedToGramPanchayat(String riReportedToGramPanchayat) {
        this.riReportedToGramPanchayat = riReportedToGramPanchayat;
    }

    public String getRiTimeToRespond() {
        return riTimeToRespond;
    }

    public void setRiTimeToRespond(String riTimeToRespond) {
        this.riTimeToRespond = riTimeToRespond;
    }

    public String getRiReferredToHF() {
        return riReferredToHF;
    }

    public void setRiReferredToHF(String riReferredToHF) {
        this.riReferredToHF = riReferredToHF;
    }

    public String getRiReferredForMedical() {
        return riReferredForMedical;
    }

    public void setRiReferredForMedical(String riReferredForMedical) {
        this.riReferredForMedical = riReferredForMedical;
    }

    public String getRiReferredToCOManager() {
        return riReferredToCOManager;
    }

    public void setRiReferredToCOManager(String riReferredToCOManager) {
        this.riReferredToCOManager = riReferredToCOManager;
    }

    public String getRiReferredToheadOfGP() {
        return riReferredToheadOfGP;
    }

    public void setRiReferredToheadOfGP(String riReferredToheadOfGP) {
        this.riReferredToheadOfGP = riReferredToheadOfGP;
    }

    public String getRiReferredToPLV() {
        return riReferredToPLV;
    }

    public void setRiReferredToPLV(String riReferredToPLV) {
        this.riReferredToPLV = riReferredToPLV;
    }

    public String getRiReferredToLegalAidClinic() {
        return riReferredToLegalAidClinic;
    }

    public void setRiReferredToLegalAidClinic(String riReferredToLegalAidClinic) {
        this.riReferredToLegalAidClinic = riReferredToLegalAidClinic;
    }

    public String getRiReferredToPolice() {
        return riReferredToPolice;
    }

    public void setRiReferredToPolice(String riReferredToPolice) {
        this.riReferredToPolice = riReferredToPolice;
    }

    public String getRiReferredToSHG() {
        return riReferredToSHG;
    }

    public void setRiReferredToSHG(String riReferredToSHG) {
        this.riReferredToSHG = riReferredToSHG;
    }

    public String getRiEmergencyActionTaken() {
        return riEmergencyActionTaken;
    }

    public void setRiEmergencyActionTaken(String riEmergencyActionTaken) {
        this.riEmergencyActionTaken = riEmergencyActionTaken;
    }

    public String getRiReferredToHospital() {
        return riReferredToHospital;
    }

    public void setRiReferredToHospital(String riReferredToHospital) {
        this.riReferredToHospital = riReferredToHospital;
    }

    public String getRiComplaintMadeInSW() {
        return riComplaintMadeInSW;
    }

    public void setRiComplaintMadeInSW(String riComplaintMadeInSW) {
        this.riComplaintMadeInSW = riComplaintMadeInSW;
    }

    public String getRiCommunityMobilized() {
        return riCommunityMobilized;
    }

    public void setRiCommunityMobilized(String riCommunityMobilized) {
        this.riCommunityMobilized = riCommunityMobilized;
    }

    public String getRiPrioritizedSAWF() {
        return riPrioritizedSAWF;
    }

    public void setRiPrioritizedSAWF(String riPrioritizedSAWF) {
        this.riPrioritizedSAWF = riPrioritizedSAWF;
    }

    public String getRiCurrentStatus() {
        return riCurrentStatus;
    }

    public void setRiCurrentStatus(String riCurrentStatus) {
        this.riCurrentStatus = riCurrentStatus;
    }

    public String getRiFollowUpNeeded() {
        return riFollowUpNeeded;
    }

    public void setRiFollowUpNeeded(String riFollowUpNeeded) {
        this.riFollowUpNeeded = riFollowUpNeeded;
    }

    public String getRiFollowUpDate() {
        return riFollowUpDate;
    }

    public void setRiFollowUpDate(String riFollowUpDate) {
        this.riFollowUpDate = riFollowUpDate;
    }

    public String getRiClosureDate() {
        return riClosureDate;
    }

    public void setRiClosureDate(String riClosureDate) {
        this.riClosureDate = riClosureDate;
    }

    public String getRiCreate() {
        return riCreate;
    }

    public void setRiCreate(String riCreate) {
        this.riCreate = riCreate;
    }

    public String getRiUpdate() {
        return riUpdate;
    }

    public void setRiUpdate(String riUpdate) {
        this.riUpdate = riUpdate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedByRole() {
        return createdByRole;
    }

    public void setCreatedByRole(String createdByRole) {
        this.createdByRole = createdByRole;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedByRole() {
        return lastModifiedByRole;
    }

    public void setLastModifiedByRole(String lastModifiedByRole) {
        this.lastModifiedByRole = lastModifiedByRole;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
