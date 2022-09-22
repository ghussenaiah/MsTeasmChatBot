package com.microsoft.teams.app.entity;

import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonProperty;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Id;

import javax.persistence.Lob;
import javax.persistence.Table;




import javax.persistence.Entity;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "\"user\"")
@JsonIgnoreProperties(ignoreUnknown = true)

public class User implements Serializable {

    @Column(name = "id")
    @Id
    @JsonProperty("id")
    private String id;

    @Column(name = "vehicletypeid")
    @JsonProperty("vehicleTypeId")
    private String vehicleTypeId;
    @Column(name = "k_companyid")
    @JsonProperty("k_companyId")
    private String k_companyId;
    @Column(name = "digitalsignature")
    @JsonProperty("digitalSignature")
    private String digitalSignature;
    @Column(name = "isactive")
    @JsonProperty("isactive")
    private String isactive;

    @Column(name = "departmentid")
    @JsonProperty("departmentId")
    private String departmentId;

    @Column(nullable = false, name = "employeecode")
    @JsonProperty("employeeCode")
    private String employeeCode;
    @Column(name = "userdomain")
    @JsonProperty("userdomain")
    private String userdomain;
    @Column(name = "organizationid")
    @JsonProperty("organizationId")
    private String organizationId;
    @Column(name = "k_costcenterid")
    @JsonProperty("k_costcenterId")
    private String k_costcenterId;
    @Column(name = "password")
    @JsonProperty("password")
    private String password;
    @Column(name = "entitlmentsid")
    @JsonProperty("entitlmentsId")
    private String entitlmentsId;

    @Column(name = "useremail")
    @JsonProperty("userEmail")
    private String userEmail;

    @Column(name = "reportingemployeeuserid")
    @JsonProperty("reportingEmployeeUserId")
    private String reportingEmployeeUserId;
    @Column(name = "gradeid")
    @JsonProperty("gradeId")
    private String gradeId;
    @Column(name = "designationid")
    @JsonProperty("designationId")
    private String designationId;
    @Column(name = "profile")
    @JsonProperty("profile")
    private String profile;
    @Column(name = "processlistid")
    @JsonProperty("processListId")
    private String processListId;

    @Column(nullable = false, name = "username")
    @JsonProperty("userName")
    private String userName;

    @Column(nullable = false, name = "userid")
    @JsonProperty("userId")
    private String userId;
    @Column(name = "k_divisionid")
    @JsonProperty("k_divisionId")
    private String k_divisionId;
    @Column(name = "vehicleno")
    @JsonProperty("vehicleNo")
    private String vehicleNo;
    @Column(name = "toggleid")
    @JsonProperty("toggleId")
    private String toggleId;

    @Column(name = "phone")
    @JsonProperty("phone")
    private String phone;
    @Column(name = "k_vendorid")
    @JsonProperty("k_vendorId")
    private String k_vendorId;
    @Column(name = "errormessage")
    @Lob
    @JsonProperty("errorMessage")
    private String errorMessage;

    @Column(name = "openbravologin")
    @JsonProperty("openbravologin")
    private String openbravologin;
    @Column(name = "entitlmentscopyid")
    @JsonProperty("entitlmentsCopyId")
    private String entitlmentsCopyId;
    @Column(name = "teamsid")
    @JsonProperty("teamsId")
    private String teamsId;
	/*
	 * @JsonSerialize(as = java.util.LinkedHashSet.class)
	 * 
	 * @JsonDeserialize(as = java.util.LinkedHashSet.class)
	 * 
	 * @Fetch(FetchMode.SELECT)
	 * 
	 * @ManyToMany(fetch = FetchType.EAGER)
	 * 
	 * @JoinTable(joinColumns = @JoinColumn(referencedColumnName = "id", name =
	 * "userid"), inverseJoinColumns = @JoinColumn(referencedColumnName = "id", name
	 * = "roleid"), name = "user__role")
	 * 
	 * @JsonProperty("Role") private Set<Role> Role = new LinkedHashSet<>();
	 * 
	 * 
	 * @ManyToOne(fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(referencedColumnName = "id", name = "departmentid", updatable =
	 * false, foreignKey = @ForeignKey(name = "FK_UserDepartment_23"), insertable =
	 * false)
	 * 
	 * @JsonProperty("Department_23") private SupportDepartment_311 Department_23;
	 */
    
	/*
	 * @JsonIgnore public Set<Role> getRole() { return Role; }
	 * 
	 * @JsonIgnore public void setRole(Set<Role> Role) { this.Role = Role; }
	 */


    @JsonIgnore
    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    @JsonIgnore
    public void setVehicleTypeId(String vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    @JsonIgnore
    public String getK_companyId() {
        return k_companyId;
    }

    @JsonIgnore
    public void setK_companyId(String k_companyId) {
        this.k_companyId = k_companyId;
    }

    @JsonIgnore
    public String getDigitalSignature() {
        return digitalSignature;
    }

    @JsonIgnore
    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    @JsonIgnore
    public String getIsactive() {
        return isactive;
    }

    @JsonIgnore
    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    @JsonIgnore
    public String getDepartmentId() {
        return departmentId;
    }

    @JsonIgnore
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @JsonIgnore
    public String getEmployeeCode() {
        return employeeCode;
    }

    @JsonIgnore
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    @JsonIgnore
    public String getUserdomain() {
        return userdomain;
    }

    @JsonIgnore
    public void setUserdomain(String userdomain) {
        this.userdomain = userdomain;
    }

    @JsonIgnore
    public String getOrganizationId() {
        return organizationId;
    }

    @JsonIgnore
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @JsonIgnore
    public String getK_costcenterId() {
        return k_costcenterId;
    }

    @JsonIgnore
    public void setK_costcenterId(String k_costcenterId) {
        this.k_costcenterId = k_costcenterId;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getEntitlmentsId() {
        return entitlmentsId;
    }

    @JsonIgnore
    public void setEntitlmentsId(String entitlmentsId) {
        this.entitlmentsId = entitlmentsId;
    }

    @JsonIgnore
    public String getUserEmail() {
        return userEmail;
    }

    @JsonIgnore
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getReportingEmployeeUserId() {
        return reportingEmployeeUserId;
    }

    @JsonIgnore
    public void setReportingEmployeeUserId(String reportingEmployeeUserId) {
        this.reportingEmployeeUserId = reportingEmployeeUserId;
    }

    @JsonIgnore
    public String getGradeId() {
        return gradeId;
    }

    @JsonIgnore
    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    @JsonIgnore
    public String getDesignationId() {
        return designationId;
    }

    @JsonIgnore
    public void setDesignationId(String designationId) {
        this.designationId = designationId;
    }

    @JsonIgnore
    public String getProfile() {
        return profile;
    }

    @JsonIgnore
    public void setProfile(String profile) {
        this.profile = profile;
    }

    @JsonIgnore
    public String getProcessListId() {
        return processListId;
    }

    @JsonIgnore
    public void setProcessListId(String processListId) {
        this.processListId = processListId;
    }

    @JsonIgnore
    public String getUserName() {
        return userName;
    }

    @JsonIgnore
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonIgnore
    public String getUserId() {
        return userId;
    }

    @JsonIgnore
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonIgnore
    public String getK_divisionId() {
        return k_divisionId;
    }

    @JsonIgnore
    public void setK_divisionId(String k_divisionId) {
        this.k_divisionId = k_divisionId;
    }

    @JsonIgnore
    public String getVehicleNo() {
        return vehicleNo;
    }

    @JsonIgnore
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    @JsonIgnore
    public String getToggleId() {
        return toggleId;
    }

    @JsonIgnore
    public void setToggleId(String toggleId) {
        this.toggleId = toggleId;
    }

    @JsonIgnore
    public String getPhone() {
        return phone;
    }

    @JsonIgnore
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonIgnore
    public String getK_vendorId() {
        return k_vendorId;
    }

    @JsonIgnore
    public void setK_vendorId(String k_vendorId) {
        this.k_vendorId = k_vendorId;
    }

    @JsonIgnore
    public String getErrorMessage() {
        return errorMessage;
    }

    @JsonIgnore
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonIgnore
    public String getOpenbravologin() {
        return openbravologin;
    }

    @JsonIgnore
    public void setOpenbravologin(String openbravologin) {
        this.openbravologin = openbravologin;
    }

    @JsonIgnore
    public String getEntitlmentsCopyId() {
        return entitlmentsCopyId;
    }

    @JsonIgnore
    public void setEntitlmentsCopyId(String entitlmentsCopyId) {
        this.entitlmentsCopyId = entitlmentsCopyId;
    }

    @JsonIgnore
    public String getTeamsId() {
        return teamsId;
    }

    @JsonIgnore
    public void setTeamsId(String teamsId) {
        this.teamsId = teamsId;
    }

	/*
	 * @JsonIgnore public SupportDepartment_311 getSupportDepartment_311() { return
	 * Department_23; }
	 * 
	 * @JsonIgnore public void setSupportDepartment_311(SupportDepartment_311
	 * Department_23) { this.Department_23 = Department_23; }
	 */
    


    @JsonIgnore
    public static User loadDefaultValues(String offset) {
        User entity = new User();
        entity.setIsactive("Yes");
        entity.setEmployeeCode("");
        entity.setUserdomain("kagami-generated_Srinivasa_Test");
        entity.setPassword("");
        entity.setUserEmail("");
        entity.setUserName("");
        entity.setUserId("");
        entity.setVehicleNo("");
        entity.setPhone("");
        entity.setErrorMessage("");
        entity.setOpenbravologin("");
        entity.setTeamsId("");
        return entity;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if (this.getId() != null)
            hashCode = hashCode + 37 + this.getId().hashCode();
        else
            hashCode = hashCode + 37;
        return hashCode;
    }

    @Override
    public boolean equals(Object object) {
        User entity = (User) object;
        if (entity == null)
            return false;
        if ((entity.getId() == null && this.getId() == null)) {
            return true;
        }
        if ((entity.getId() != null && this.getId() != null && entity.getId().equals(this.getId()))) {
            return true;
        } else
            return false;
    }

    @Override
    public String toString() {
        String logResult = "User[vehicleTypeId = " + vehicleTypeId + ", k_companyId = " + k_companyId
                + ", digitalSignature = " + digitalSignature + ", isactive = " + isactive + ", departmentId = "
                + departmentId + ", employeeCode = " + employeeCode + ", userdomain = " + userdomain
                + ", organizationId = " + organizationId + ", k_costcenterId = " + k_costcenterId + ", password = "
                + password + ", entitlmentsId = " + entitlmentsId + ", userEmail = " + userEmail + ", id = " + id
                + ", reportingEmployeeUserId = " + reportingEmployeeUserId + ", gradeId = "
                + gradeId + ", designationId = " + designationId + ", profile = " + profile + ", processListId = "
                + processListId + ", userName = " + userName + ", userId = " + userId + ", k_divisionId = "
                + k_divisionId + ", vehicleNo = " + vehicleNo + ", toggleId = " + toggleId + ", phone = " + phone
                + ", k_vendorId = " + k_vendorId + ", errorMessage = " + errorMessage + ", openbravologin = "
                + openbravologin + ", entitlmentsCopyId = " + entitlmentsCopyId + ", teamsId = " + teamsId + ", ]";
        return logResult;
    }
}
