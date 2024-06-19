package com.example.FairPay.Models.DB;

import com.example.FairPay.Models.Types.Participant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.mail.Part;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "groupMembers")
public class GroupMembers {
    @Id
    private String _id;

    @Field
    private List<Participant> groupParticipants = new ArrayList<Participant>();

    public GroupMembers(String _id,  List<Participant> groupParticipants) {
        this._id = _id;
        this.groupParticipants = groupParticipants;
    }

    public GroupMembers() {
    }

    public GroupMembers(String member, float v) {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Participant> getGroupParticipants() {
        return groupParticipants;
    }

    public void setGroupParticipants(List<Participant> groupParticipants) {
        this.groupParticipants = groupParticipants;
    }
}
