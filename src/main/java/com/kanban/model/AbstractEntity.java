package com.kanban.model;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Software_Development on 30-Nov-16.
 */

@MappedSuperclass
public abstract class AbstractEntity implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date created;
    private Date updated;

    private String createdBy;
    private String updatedBy;
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPersistent() {
        return id != null;
    }

    @PrePersist
    protected void onCreate() {
        created = new Date();
        updated = created;
//        try {
//            if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
//                createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
//                updatedBy = createdBy;
//            }
//        } catch (Exception e) {
//            System.out.println("Not Authentication for save AbstractEntity on DataLoader");
//        }
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
//        try {
//            if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
//                updatedBy = SecurityContextHolder.getContext().getAuthentication().getName();
//            }
//        } catch (Exception e) {
//            System.out.println("Not Authentication for update AbstractEntity on DataLoader");
//        }
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreated() {
        return created;
    }

    public String getCreatedFormat() {
        DateFormat sdfFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        return sdfFormat.format(created);
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getUpdatedFormat() {
        DateFormat sdfFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        return sdfFormat.format(updated);
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(this.id == null) {
            return false;
        }

        if (obj instanceof AbstractEntity && obj.getClass().equals(getClass())) {
            return this.id.equals(((AbstractEntity) obj).id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
