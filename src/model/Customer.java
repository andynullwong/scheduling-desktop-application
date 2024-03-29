package model;

import dao.FirstLevelDivisionDao;
import dao.LoginDao;
import utils.TimezoneUtil;

import java.sql.Timestamp;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;
    private String formattedAddress;

    public Customer() {
        this.createDate = TimezoneUtil.getUTCTime();
        this.lastUpdate = TimezoneUtil.getUTCTime();
        this.createdBy = LoginDao.getCurrentUser().getName();
        this.lastUpdatedBy = LoginDao.getCurrentUser().getName();
        this.formattedAddress = getFormattedAddress();
    }

    public Customer(int id, String name, String address, String postalCode, String phone, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.formattedAddress = getFormattedAddress();
    }

    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.formattedAddress = getFormattedAddress();
    }

    public Customer(String name, String address, String postalCode, String phone, int divisionId) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.createDate = TimezoneUtil.getUTCTime();
        this.lastUpdate = TimezoneUtil.getUTCTime();
        this.createdBy = LoginDao.getCurrentUser().getName();
        this.lastUpdatedBy = LoginDao.getCurrentUser().getName();
        this.formattedAddress = getFormattedAddress();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getFormattedAddress() {
        return address + ", " + FirstLevelDivisionDao.getDivisionFromId(divisionId);
    }
}
