package uk.co.jakestanley.commander.scene.entities.mobiles;

import lombok.Getter;
import lombok.Setter;
import uk.co.jakestanley.commander.scene.entities.PhysicalEntity;
import uk.co.jakestanley.commander.scene.entities.shapes.Shape;

/**
 * Created by jp-st on 08/11/2015.
 */
@Getter @Setter
public class Mobile extends PhysicalEntity {

    private float hSpeed; // TODO create shape templates
    private float vSpeed;

    public Mobile(String id, Shape shape, float hSpeed, float vSpeed) {
        super(id, shape);
        this.hSpeed = hSpeed;
        this.vSpeed = vSpeed;
    }

    public boolean canMovePositiveX(){
        return true; // TODO functionality. make sure there is no collision between here and the target position
    }

    public boolean canMoveNegativeX(){
//        Main.sceneController.();
        return true; // TODO functionality
    }

    public boolean canMovePositiveZ(){
        return true; // TODO functionality
    }

    public boolean canMoveNegativeZ(){
        return true; // TODO functionality
    }

    public boolean canMovePositiveY(){
        return true; // TODO functionality
    }

    public boolean canMoveNegativeY(){
        return true; // TODO functionality
    }

    public boolean canMovePositiveX(float distance){
        return true; // TODO functionality. make sure there is no collision between here and the target position
    }

    public boolean canMoveNegativeX(float distance){
//        Main.sceneController.();
        return true; // TODO functionality
    }

    public boolean canMovePositiveZ(float distance){
        return true; // TODO functionality
    }

    public boolean canMoveNegativeZ(float distance){
        return true; // TODO functionality
    }

    public boolean canMovePositiveY(float distance){
        return true; // TODO functionality
    }

    public boolean canMoveNegativeY(float distance){
        return true; // TODO functionality
    }


    public boolean movePositiveX(){
        shape.setXLocal(shape.getXLocal() + hSpeed);
        return true;
    }

    public boolean moveNegativeX(){
        shape.setXLocal(shape.getXLocal() - hSpeed);
        return true;
    }

    public boolean movePositiveZ(){
        shape.setZLocal(shape.getZLocal() + hSpeed);
        return true;
    }

    public boolean moveNegativeZ(){
        shape.setZLocal(shape.getZLocal() - hSpeed);
        return true;
    }

    public boolean movePositiveY(){
        shape.setYLocal(shape.getYLocal() + vSpeed);
        return true;
    }

    public boolean moveNegativeY(){
        shape.setYLocal(shape.getYLocal() - vSpeed);
        return true;
    }

    public boolean movePositiveX(float distance){
        // TODO safety
        return true;
    }

    public boolean moveNegativeX(float distance){

        return true;
    }

    public boolean movePositiveZ(float distance){

        return true;
    }

    public boolean moveNegativeZ(float distance){

        return true;
    }

    public boolean movePositiveY(float distance){

        return true;
    }

    public boolean moveNegativeY(float distance){

        return true;
    }

}