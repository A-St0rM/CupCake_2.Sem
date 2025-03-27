package app.entities;

public class Status {

    private int status_id;
    private boolean is_paid;

    public Status(int status_id, boolean is_paid) {
        this.status_id = status_id;
        this.is_paid = is_paid;
    }
    public Status( boolean is_paid) {
        this.is_paid = is_paid;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public boolean isIs_paid() {
        return is_paid;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
    }
}
