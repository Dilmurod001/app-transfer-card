package uz.pdp.apptransfercard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.apptransfercard.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Outcome extends AbsEntity {
    @ManyToOne
    private Card fromCard;
    @ManyToOne
    private Card toCard;
    private Double amount;
    private Date date;
    private Double commissionAmount=1d;
}
