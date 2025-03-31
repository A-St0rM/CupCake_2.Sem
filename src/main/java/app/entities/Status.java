package app.entities;

public class Status {

    private int statusId;
    private boolean isPaid;
    private boolean isPickedUp;

    public Status(int statusId, boolean isPaid, boolean isPickedUp) {
        this.statusId = statusId;
        this.isPaid = isPaid;
        this.isPickedUp = isPickedUp;
    }
    public Status(boolean isPaid, boolean isPickedUp) {
        this.isPaid = isPaid;
        this.isPickedUp = isPickedUp;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        this.isPaid = paid;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        isPickedUp = pickedUp;
    }
}
