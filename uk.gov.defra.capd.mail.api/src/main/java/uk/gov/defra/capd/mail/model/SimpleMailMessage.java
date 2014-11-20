package uk.gov.defra.capd.mail.model;

import java.util.Arrays;
import java.util.Date;

/**
 * DTO representing message to be sent via mail service
 */
public class SimpleMailMessage {

    private String from;

    private String replyTo;

    private String[] to;

    private String[] cc;

    private String[] bcc;

    private Date sentDate;

    private String subject;

    private String text;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleMailMessage that = (SimpleMailMessage) o;

        if (!Arrays.equals(bcc, that.bcc)) return false;
        if (!Arrays.equals(cc, that.cc)) return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (replyTo != null ? !replyTo.equals(that.replyTo) : that.replyTo != null) return false;
        if (sentDate != null ? !sentDate.equals(that.sentDate) : that.sentDate != null) return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (!Arrays.equals(to, that.to)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (replyTo != null ? replyTo.hashCode() : 0);
        result = 31 * result + (to != null ? Arrays.hashCode(to) : 0);
        result = 31 * result + (cc != null ? Arrays.hashCode(cc) : 0);
        result = 31 * result + (bcc != null ? Arrays.hashCode(bcc) : 0);
        result = 31 * result + (sentDate != null ? sentDate.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}

