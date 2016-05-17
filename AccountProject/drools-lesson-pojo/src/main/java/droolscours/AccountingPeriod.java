package droolscours;

import java.text.DateFormat;
import java.util.Date;

public class AccountingPeriod {
    private Date startDate;
    private Date endDate;

    public AccountingPeriod() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AccountingPeriod(Date startDate, Date endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date start) {
        this.startDate = start;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date end) {
        this.endDate = end;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuffer buff = new StringBuffer();
        buff.append("-----AccountingPeriod-----)\n");
        if (this.startDate != null) {
            buff.append("StartDate "
                    + DateFormat.getDateInstance().format(this.startDate)
                    + "\n");
        } else {
            buff.append("No start date was set\n");
        }
        if (this.endDate != null) {
            buff.append("EndDate  "
                    + DateFormat.getDateInstance().format(this.endDate) + "\n");
        } else {
            buff.append("No ens date was set\n");
        }
        buff.append("-----End AccountingPeriod -)");
        return buff.toString();
    }

}
