package org.ohd.umls;

import gov.nih.nlm.nls.lvg.Api.LuiNormApi;

public class Coder {

    LuiNormApi myLui;
    String conf = "lvg_db.cfg";  //default config location

    public Coder(String config) {
        conf = config;
        makeLui();
    }

    public Coder() {
        makeLui();
    }

    private void makeLui() {
        myLui = new LuiNormApi(conf);
    }

    public String normalize(String str) {
        try {
            return myLui.Mutate(str);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    public String getCui(String str) {
        String sql = "SELECT DISTINCT mrxns_eng.cui FROM mrxns_eng WHERE mrxns_eng.nstr = '"+str+"' AND mrxns_eng.lat = 'eng'";
        return null;
    }

    public String getCode(String cui, String vocab) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
