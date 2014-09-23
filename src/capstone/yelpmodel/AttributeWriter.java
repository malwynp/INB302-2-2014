/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.yelpmodel;

import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public abstract class AttributeWriter {
    
    private String attributeName, attributeType;
    
    public AttributeWriter(String attributeName, String attributeType) {
        setAttributeName(attributeName);
        setAttributeType(attributeType);
    }
    
    public void setAttributeName(String attr) {
        attributeName = attr;
    }
    public void setAttributeType(String attr) {
        attributeType = attr;
    }
    public String getAttributeName() {
        return attributeName;
    }
    public String getAttributeType() {
        return attributeType;
    }
    
    public String getAttributeDefinition() {
        return "@ATTRIBUTE\t" + getAttributeName() + "\t" + getAttributeType() + "\n";
    }
    public abstract String getAttributeFor(JSONObject obj);
    
    public class JSONAttribute extends AttributeWriter {

        public JSONAttribute(String attributeName, String attributeType) {
            super(attributeName, attributeType);
        }

        public String getAttributeFor(JSONObject obj) {
            return (String) obj.get(getAttributeName());
        }
        
    }
    
}
