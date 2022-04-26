package com.jmshop.jmshop_admin.listener;

import com.jmshop.jmshop_admin.dto.domain.Product;
import javax.persistence.PrePersist;
import java.util.Date;

public class DomainEventListener {
    @PrePersist
    public void prePersist(Object obj) {
        if (obj instanceof Product) {
            ((Product) obj).setCreatedDate(new Date());
            ((Product) obj).setModifiedDate(new Date());
        }
    }
}
