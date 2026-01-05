import model.ArchiveCustomer;
import model.Customer;

public class GetMessageState {
    private boolean isCreateCustomer;
    private boolean isWaitingName;
    private boolean isWaitingPhone;
    private boolean isWaitingCity;
    private boolean isWaitingType;
    private boolean isWaitingId;
    private boolean isWaitingTime;
    private Boolean isTimeYourself;
    private Boolean isCreateCustomerInDB;
    private Boolean isChoiceVersion;
    private Boolean isFullVersion;
    private Boolean isShortVersion;
    private boolean isLocation;
    private boolean isFormOfPayment;
    private boolean isHeatingLevel;
    private boolean isWaitingFlatType;
    private boolean isLayout;
    private boolean showTimeSelection;
    private boolean processTimeSelection;
    private boolean layoutYourself;
    private boolean shortDescription;
    private boolean needSelectByPhoneNumberForCustomer;
    private boolean needSelectByPhoneNumberForArhiveCustomer;
    private Customer customerForSendInArchive;
    private ArchiveCustomer archiveCustomerForSendInQuestionableCustomer;
    private boolean needTimeForTransferedCustomer;

    public GetMessageState() {
        isCreateCustomer = false;
        isWaitingName = false;
        isWaitingPhone = false;
        isWaitingCity = false;
        isWaitingType = false;
        isWaitingId = false;
        isWaitingTime = false;
        isTimeYourself = false;
        isCreateCustomerInDB = false;
        isChoiceVersion = false;
        isFullVersion = false;
        isShortVersion = false;
        isLocation = false;
        isFormOfPayment = false;
        isHeatingLevel = false;
        isWaitingFlatType = false;
        isLayout = false;
        showTimeSelection = false;
        processTimeSelection = false;
        layoutYourself = false;
        shortDescription = false;
        needSelectByPhoneNumberForCustomer = false;
        needSelectByPhoneNumberForArhiveCustomer = false;
        customerForSendInArchive = null;
        archiveCustomerForSendInQuestionableCustomer = null;
        needTimeForTransferedCustomer = false;

    }

    public void reset(){
        isCreateCustomer = false;
        isWaitingName = false;
        isWaitingPhone = false;
        isWaitingCity = false;
        isWaitingType = false;
        isWaitingId = false;
        isWaitingTime = false;
        isTimeYourself = false;
        isCreateCustomerInDB = false;
        isChoiceVersion = false;
        isFullVersion = false;
        isShortVersion = false;
        isLocation = false;
        isFormOfPayment = false;
        isHeatingLevel = false;
        isWaitingFlatType = false;
        isLayout = false;
        showTimeSelection = false;
        processTimeSelection = false;
        layoutYourself = false;
        needSelectByPhoneNumberForCustomer = false;
        needSelectByPhoneNumberForArhiveCustomer = false;
        customerForSendInArchive = null;
        archiveCustomerForSendInQuestionableCustomer = null;
        needTimeForTransferedCustomer = false;
    }

    public boolean isNeedTimeForTransferedCustomer() {
        return needTimeForTransferedCustomer;
    }

    public void setNeedTimeForTransferedCustomer(boolean needTimeForTransferedCustomer) {
        this.needTimeForTransferedCustomer = needTimeForTransferedCustomer;
    }

    public ArchiveCustomer getArchiveCustomerForSendInQuestionableCustomer() {
        return archiveCustomerForSendInQuestionableCustomer;
    }

    public void setArchiveCustomerForSendInQuestionableCustomer(ArchiveCustomer archiveCustomerForSendInQuestionableCustomer) {
        this.archiveCustomerForSendInQuestionableCustomer = archiveCustomerForSendInQuestionableCustomer;
    }

    public Customer getCustomerForSendInArchive() {
        return customerForSendInArchive;
    }
    public void setCustomerForSendInArchive(Customer customerForSendInArchive) {
        this.customerForSendInArchive = customerForSendInArchive;
    }


    public boolean isNeedSelectByPhoneNumberForArhiveCustomer() {
        return needSelectByPhoneNumberForArhiveCustomer;
    }

    public void setNeedSelectByPhoneNumberForArhiveCustomer(boolean needSelectByPhoneNumberForArhiveCustomer) {
        this.needSelectByPhoneNumberForArhiveCustomer = needSelectByPhoneNumberForArhiveCustomer;
    }

    public boolean isNeedSelectByPhoneNumberForCustomer() {
        return needSelectByPhoneNumberForCustomer;
    }

    public void setNeedSelectByPhoneNumberForCustomer(boolean needSelectByPhoneNumberForCustomer) {
        this.needSelectByPhoneNumberForCustomer = needSelectByPhoneNumberForCustomer;
    }

    public boolean isCreateCustomer() {
        return isCreateCustomer;
    }

    public void setCreateCustomer(boolean createCustomer) {
        isCreateCustomer = createCustomer;
    }

    public boolean isWaitingName() {
        return isWaitingName;
    }

    public void setWaitingName(boolean waitingName) {
        isWaitingName = waitingName;
    }

    public boolean isWaitingPhone() {
        return isWaitingPhone;
    }

    public void setWaitingPhone(boolean waitingPhone) {
        isWaitingPhone = waitingPhone;
    }

    public boolean isWaitingCity() {
        return isWaitingCity;
    }

    public void setWaitingCity(boolean waitingCity) {
        isWaitingCity = waitingCity;
    }

    public boolean isWaitingType() {
        return isWaitingType;
    }

    public void setWaitingType(boolean waitingType) {
        isWaitingType = waitingType;
    }

    public boolean isWaitingId() {
        return isWaitingId;
    }

    public void setWaitingId(boolean waitingId) {
        isWaitingId = waitingId;
    }

    public boolean isWaitingTime() {
        return isWaitingTime;
    }

    public void setWaitingTime(boolean waitingTime) {
        isWaitingTime = waitingTime;
    }

    public Boolean isTimeYourself() {
        return isTimeYourself;
    }

    public void setTimeYourself(Boolean timeYourself) {
        isTimeYourself = timeYourself;
    }

    public Boolean isCreateCustomerInDB() {
        return isCreateCustomerInDB;
    }

    public void setCreateCustomerInDB(Boolean createUser) {
        isCreateCustomerInDB = createUser;
    }

    public Boolean isChoiceVersion() {
        return isChoiceVersion;
    }

    public void setChoiceVersion(Boolean choiceVersion) {
        isChoiceVersion = choiceVersion;
    }

    public Boolean getFullVersion() {
        return isFullVersion;
    }

    public void setFullVersion(Boolean fullVersion) {
        isFullVersion = fullVersion;
    }

    public Boolean isShortVersion() {
        return isShortVersion;
    }

    public void setShortVersion(Boolean shortVersion) {
        isShortVersion = shortVersion;
    }

    public Boolean getTimeYourself() {
        return isTimeYourself;
    }

    public Boolean getChoiceVersion() {
        return isChoiceVersion;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }

    public boolean isFormOfPayment() {
        return isFormOfPayment;
    }

    public void setFormOfPayment(boolean formOfPayment) {
        isFormOfPayment = formOfPayment;
    }

    public boolean isHeatingLevel() {
        return isHeatingLevel;
    }

    public void setHeatingLevel(boolean heatingLevel) {
        isHeatingLevel = heatingLevel;
    }

    public boolean isWaitingFlatType() {
        return isWaitingFlatType;
    }

    public void setWaitingFlatType(boolean waitingFlatType) {
        this.isWaitingFlatType = waitingFlatType;
    }

    public boolean isLayout() {
        return isLayout;
    }

    public void setLayout(boolean layout) {
        isLayout = layout;
    }

    public boolean isProcessTimeSelection() {
        return processTimeSelection;
    }

    public void setProcessTimeSelection(boolean processTimeSelection) {
        this.processTimeSelection = processTimeSelection;
    }

    public boolean isShowTimeSelection() {
        return showTimeSelection;
    }

    public void setShowTimeSelection(boolean showTimeSelection) {
        this.showTimeSelection = showTimeSelection;
    }

    public boolean isLayoutYourself() {
        return layoutYourself;
    }

    public void setLayoutYourself(boolean layoutYourself) {
        this.layoutYourself = layoutYourself;
    }

    public boolean isShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(boolean shortDescription) {
        this.shortDescription = shortDescription;
    }
}