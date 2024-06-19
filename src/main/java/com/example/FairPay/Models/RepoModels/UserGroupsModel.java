package com.example.FairPay.Models.RepoModels;

import com.example.FairPay.Models.Types.GroupUser;

import java.util.List;

public class UserGroupsModel {
    private String _id;

    private List<GroupUser> list;

    public UserGroupsModel(String _id, List<GroupUser> list) {
        this._id = _id;
        this.list = list;
    }

    public UserGroupsModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<GroupUser> getList() {
        return list;
    }

    public void setList(List<GroupUser> list) {
        this.list = list;
    }
}
