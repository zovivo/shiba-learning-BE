package com.shibalearning.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    @Column(name = "createTime")
    @JsonFormat
    public Timestamp createTime;
    @Column(name = "updateTime")
    @JsonFormat
    public Timestamp updateTime;

    @PrePersist
    public void prePersist() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));
        Timestamp date = new Timestamp(cal.getTimeInMillis());
        this.createTime = date;
    }

    @PreUpdate
    public void preUpdate() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));
        Timestamp date = new Timestamp(cal.getTimeInMillis());
        this.updateTime = date;
    }
}
