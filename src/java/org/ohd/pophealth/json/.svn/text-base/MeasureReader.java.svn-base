/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ohd.pophealth.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.ohd.pophealth.json.measuremodel.CodedValue;
import org.ohd.pophealth.json.measuremodel.Measure;
import org.ohd.pophealth.json.measuremodel.QualityMeasure;
import org.ohd.pophealth.ccr.importer.NonSupportedCategoryException;

/**
 *  This class handles the reading of quality measures in JSON
 * @author ohdohd
 */
public class MeasureReader {

    private final static Logger LOG = Logger.getLogger(MeasureReader.class.getName());
    static ObjectMapper om = new ObjectMapper();

    /**
     * Create a QualityMeasure object from a JSON representation of a quality measure
     * @param json The JSON String as defined by popHealth
     * @return
     * @throws Exception  If there is any processing problems an Exception is thrown
     */
    public static QualityMeasure extractQualityMeasure(String json) throws Exception {
        QualityMeasure qm = new QualityMeasure();
        ArrayList<Measure> m = new ArrayList<Measure>();
        JsonNode root = om.readValue(json, JsonNode.class);
        qm.setId(root.path("id").getTextValue());
        qm.setName(root.path("name").getTextValue());
        qm.setDescription(root.path("description").getTextValue());
        qm.setCategory(root.path("category").getTextValue());
        qm.setSteward(root.path("steward").getTextValue());
        JsonNode mNode = root.path("measure");
        // TODO convert to use getElements to improve performance
        Iterator<String> mDefs = mNode.getFieldNames();
        while (mDefs.hasNext()) {
            String subm = mDefs.next();
            LOG.log(Level.FINE, "Extracting submeasure: {0}", subm);
            m.add(extractMeasure(subm, mNode));
        }
        qm.setMeasures(m);
        return qm;
    }

    /*
     * Extracts the "measure" child object from the JSON
     */
    private static Measure extractMeasure(String measureName, JsonNode mNode) throws Exception {
        Measure m = new Measure();
        m.setName(measureName);
        JsonNode subM = mNode.path(measureName);
        m.setDescription(subM.path("description").getTextValue());

        // Set the type of measure
        String itemsType = (subM.path("type").getTextValue());
        m.setItemType(extractItems(itemsType, subM.path("items")));


        m.setCategory(extractCategories(subM));

        // Set Codes
        Iterator<JsonNode> codes = subM.path("codes").getElements();
        while (codes.hasNext()) {
            m.addCode(extractCode(codes.next()));
        }

        return m;
    }

    /*
     * Creates a CodedValue object from a codeset in the quality measure JSON
     */
    protected static CodedValue extractCode(JsonNode c) throws Exception {
        CodedValue cv = new CodedValue();
        cv.setCodingSystem(c.path("set").getTextValue());
        cv.setVersion(c.path("version").getTextValue());
        JsonNode values = c.path("values");
        if (values.isArray()) {
            for (int i = 0; i < values.size(); i++) {
                cv.addValue(values.get(i).getTextValue());
            }
        } else {
            throw new Exception("invalid code node");
        }
        return cv;
    }

    /*
     * Determines the standard category for the "measure" child
     */
    protected static Measure.CAT extractCategories(JsonNode subMeasure) throws Exception {
        JsonNode cats = subMeasure.path("standard_category");

        // If there is no standard_category node retun unknown category
        if (cats.isMissingNode()) {
            LOG.warning("Missing a Standard Category");
            return Measure.CAT.Unknown;
        }

        return Measure.getCAT(cats.getTextValue());
    }

    /*
     * Determines the return type ("item") required for this "measure" child
     */
    protected static Measure.TYPE extractItems(String itemsType, JsonNode items) throws NonSupportedCategoryException {
        //throw new UnsupportedOperationException("Not yet implemented");

        // Assumes only one item per mesure/items


        // Determine which class of item it is
        if (itemsType.equals("array")) {
            if (items.path("type").getTextValue().equals("number") && items.path("format").getTextValue().equals("utc-sec")) {
                // is of type DateListItem
                return Measure.TYPE.DateItem;

            } else if (items.path("type").getTextValue().equals("object")) {
                JsonNode prop = items.path("properties");
                if (prop.isMissingNode()) {
                    throw new NonSupportedCategoryException("Unknown item array type");
                } else {
                    if (!prop.path("value").isMissingNode() && !prop.path("date").isMissingNode()) {
                        // is of type ValueDateItem
                        return Measure.TYPE.ValueDateItem;
                    } else if (!prop.path("start").isMissingNode() && !prop.path("end").isMissingNode()) {
                        // is of type DateRangeItem
                        return Measure.TYPE.DateRangeItem;
                    }
                }



            }
        } else if (itemsType.equals("boolean")) {
            return Measure.TYPE.BooleanItem;
        }
        // Must not of found a supported Item type so throw error
        throw new NonSupportedCategoryException("Unknown item type [" + itemsType + "]");
    }
}
