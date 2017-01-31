package app.pbl.hcc.pblapp;

import java.util.Dictionary;

/**
 * Created by Luis on 1/30/2017.
 */

public class Chapter {
    private int chapterCode;
    private String advisorID;
    private String presidentID;
    private String vicePresidentID;
    private String secretaryID;
    private String tresurerID;
    private String position1ID;
    private String position2ID;
    private String position3ID;
    private String position4ID;
    private String position5ID;
    private String position6ID;
    private String position7ID;
    private String position8ID;
    private String position9ID;
    private String advisorCode;
    private int presidentCode;
    private int vicePresidentCode;
    private int secretaryCode;
    private int tresurerCode;
    private int position1Code;
    private int position2Code;
    private int position3Code;
    private int position4Code;
    private int position5Code;
    private int position6Code;
    private int position7Code;
    private int position8Code;
    private int position9Code;
    private int memeberCode;
    private String school;
    private Dictionary<String, Integer> positions;

    public Chapter() {
        positions.put("author",1);
        positions.put("president",2);
        positions.put("advisor",2);
        positions.put("eboard",3);
        positions.put("officer",4);
        positions.put("member",6);
    }

    public Chapter(int chapterCode, String school, int district, int memeberCode) {
        this.chapterCode = chapterCode;
        this.school = school;
        this.district = district;
        this.memeberCode = memeberCode;
        positions.put("author",1);
        positions.put("president",2);
        positions.put("advisor",2);
        positions.put("eboard",3);
        positions.put("officer",4);
        positions.put("member",6);
    }

    public Chapter(int chapterCode, String advisorID, String presidentID, int presidentCode, String advisorCode, int vicePresidentCode, int secretaryCode, int tresurerCode, int position1Code, int position2Code, int position3Code, int position4Code, int position5Code, int position6Code, int position7Code, int position8Code, int position9Code, int memeberCode, String school, int district) {
        this.chapterCode = chapterCode;
        this.advisorID = advisorID;
        this.presidentID = presidentID;
        this.presidentCode = presidentCode;
        this.advisorCode = advisorCode;
        this.vicePresidentCode = vicePresidentCode;
        this.secretaryCode = secretaryCode;
        this.tresurerCode = tresurerCode;
        this.position1Code = position1Code;
        this.position2Code = position2Code;
        this.position3Code = position3Code;
        this.position4Code = position4Code;
        this.position5Code = position5Code;
        this.position6Code = position6Code;
        this.position7Code = position7Code;
        this.position8Code = position8Code;
        this.position9Code = position9Code;
        this.memeberCode = memeberCode;
        this.school = school;
        this.district = district;
        positions.put("author",1);
        positions.put("president",2);
        positions.put("advisor",2);
        positions.put("eboard",3);
        positions.put("officer",4);
        positions.put("member",6);
    }

    public int getChapterCode() {
        return chapterCode;
    }

    public void setChapterCode(int chapterCode) {
        this.chapterCode = chapterCode;
    }

    public String getAdvisorID() {
        return advisorID;
    }

    public void setAdvisorID(String advisorID) {
        this.advisorID = advisorID;
    }

    public String getPresidentID() {
        return presidentID;
    }

    public void setPresidentID(String presidentID) {
        this.presidentID = presidentID;
    }

    public String getVicePresidentID() {
        return vicePresidentID;
    }

    public void setVicePresidentID(String vicePresidentID) {
        this.vicePresidentID = vicePresidentID;
    }

    public String getSecretaryID() {
        return secretaryID;
    }

    public void setSecretaryID(String secretaryID) {
        this.secretaryID = secretaryID;
    }

    public String getTresurerID() {
        return tresurerID;
    }

    public void setTresurerID(String tresurerID) {
        this.tresurerID = tresurerID;
    }

    public String getPosition1ID() {
        return position1ID;
    }

    public void setPosition1ID(String position1ID) {
        this.position1ID = position1ID;
    }

    public String getPosition2ID() {
        return position2ID;
    }

    public void setPosition2ID(String position2ID) {
        this.position2ID = position2ID;
    }

    public String getPosition3ID() {
        return position3ID;
    }

    public void setPosition3ID(String position3ID) {
        this.position3ID = position3ID;
    }

    public String getPosition4ID() {
        return position4ID;
    }

    public void setPosition4ID(String position4ID) {
        this.position4ID = position4ID;
    }

    public String getPosition5ID() {
        return position5ID;
    }

    public void setPosition5ID(String position5ID) {
        this.position5ID = position5ID;
    }

    public String getPosition6ID() {
        return position6ID;
    }

    public void setPosition6ID(String position6ID) {
        this.position6ID = position6ID;
    }

    public String getPosition7ID() {
        return position7ID;
    }

    public void setPosition7ID(String position7ID) {
        this.position7ID = position7ID;
    }

    public String getPosition9ID() {
        return position9ID;
    }

    public void setPosition9ID(String position9ID) {
        this.position9ID = position9ID;
    }

    public String getPosition8ID() {
        return position8ID;
    }

    public void setPosition8ID(String position8ID) {
        this.position8ID = position8ID;
    }

    public String getAdvisorCode() {
        return advisorCode;
    }

    public void setAdvisorCode(String advisorCode) {
        this.advisorCode = advisorCode;
    }

    public int getPresidentCode() {
        return presidentCode;
    }

    public void setPresidentCode(int presidentCode) {
        this.presidentCode = presidentCode;
    }

    public int getVicePresidentCode() {
        return vicePresidentCode;
    }

    public void setVicePresidentCode(int vicePresidentCode) {
        this.vicePresidentCode = vicePresidentCode;
    }

    public int getSecretaryCode() {
        return secretaryCode;
    }

    public void setSecretaryCode(int secretaryCode) {
        this.secretaryCode = secretaryCode;
    }

    public int getTresurerCode() {
        return tresurerCode;
    }

    public void setTresurerCode(int tresurerCode) {
        this.tresurerCode = tresurerCode;
    }

    public int getPosition1Code() {
        return position1Code;
    }

    public void setPosition1Code(int position1Code) {
        this.position1Code = position1Code;
    }

    public int getPosition2Code() {
        return position2Code;
    }

    public void setPosition2Code(int position2Code) {
        this.position2Code = position2Code;
    }

    public int getPosition3Code() {
        return position3Code;
    }

    public void setPosition3Code(int position3Code) {
        this.position3Code = position3Code;
    }

    public int getPosition4Code() {
        return position4Code;
    }

    public void setPosition4Code(int position4Code) {
        this.position4Code = position4Code;
    }

    public int getPosition5Code() {
        return position5Code;
    }

    public void setPosition5Code(int position5Code) {
        this.position5Code = position5Code;
    }

    public int getPosition6Code() {
        return position6Code;
    }

    public void setPosition6Code(int position6Code) {
        this.position6Code = position6Code;
    }

    public int getPosition7Code() {
        return position7Code;
    }

    public void setPosition7Code(int position7Code) {
        this.position7Code = position7Code;
    }

    public int getPosition8Code() {
        return position8Code;
    }

    public void setPosition8Code(int position8Code) {
        this.position8Code = position8Code;
    }

    public int getPosition9Code() {
        return position9Code;
    }

    public void setPosition9Code(int position9Code) {
        this.position9Code = position9Code;
    }

    public int getMemeberCode() {
        return memeberCode;
    }

    public void setMemeberCode(int memeberCode) {
        this.memeberCode = memeberCode;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    private int district;

}

