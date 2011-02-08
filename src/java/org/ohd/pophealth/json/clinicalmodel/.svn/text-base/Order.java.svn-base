/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.json.clinicalmodel;

import java.util.ArrayList;
import org.ohd.pophealth.json.measuremodel.CodedValue;

/**
 *
 * @author ohdohd
 */
public class Order extends BaseClinicalObject{

    private long orderDate = BaseObject.maxDate;
    private ArrayList<Goal> goals;
    private ArrayList<BaseClinicalObject> orderRequests;
    public Order (String id){
        super(id);
        goals = new ArrayList<Goal>();
        orderRequests = new ArrayList<BaseClinicalObject>();
    }

    public Order(String id, ArrayList<CodedValue> type, ArrayList<CodedValue> description,
            long orderDate, ArrayList<Goal> goals, String orderrequesttype) {
        super(id, type, description);
        this.orderDate = orderDate;
        if (goals != null){
            this.goals = goals;
        }else{
            this.goals = new ArrayList<Goal>();
        }
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public void setGoals(ArrayList<Goal> goals) {
        this.goals = goals;
    }

    public void addGoal(Goal g){
        this.goals.add(g);
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public ArrayList<BaseClinicalObject> getOrderRequests() {
        return orderRequests;
    }

    public void setOrderRequests(ArrayList<BaseClinicalObject> orderRequests) {
        this.orderRequests = orderRequests;
    }

    public void addOrderRequest(BaseClinicalObject orderRequest){
        this.orderRequests.add(orderRequest);
    }
}
