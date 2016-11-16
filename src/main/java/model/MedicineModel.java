package model;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import helper.Config;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MedicineModel {
    @XmlElement
    private ObjectId id;
    @XmlElement
    private String description;
    @XmlElement
    public String medicineName;
    @XmlElement
    private String indication;
    @XmlElement
    private String contraindication;
    @XmlElement
    private String salesForm;

    public MedicineModel(
            ObjectId id,
            String medicineName,
            String indication,
            String contraindication,
            String salesForm
    ) {
        this.id = id;
        this.medicineName = medicineName;
        this.indication = indication;
        this.contraindication = contraindication;
        this.salesForm = salesForm;
    }

    public MedicineModel(
            ObjectId id,
            String medicineName,
            String description,
            String indication,
            String contraindication,
            String salesForm
    ) {
        this.id = id;
        this.description = description;
        this.medicineName = medicineName;
        this.indication = indication;
        this.contraindication = contraindication;
        this.salesForm = salesForm;
    }

    public Document toMongoDocument() {
        return new Document(Config.DB.COLUMN_ID, getId())
                .append(Config.DB.COLUMN_NAME, getMedicineName())
                .append(Config.DB.COLUMN_DESCRIPTION, getMedicineName())
                .append(Config.DB.COLUMN_ID, getIndication())
                .append(Config.DB.COLUMN_CONTRAINDICATION, getContraindication())
                .append(Config.DB.COLUMN_SALES_FORM, getSalesForm());
    }

    @Nullable
    public static MedicineModel fromMongoDocument(@NotNull final Document document) {
        return new MedicineModel(
                document.getObjectId(Config.DB.COLUMN_ID),
                document.getString(Config.DB.COLUMN_NAME),
                document.getString(Config.DB.COLUMN_DESCRIPTION),
                document.getString(Config.DB.COLUMN_INDICATION),
                document.getString(Config.DB.COLUMN_CONTRAINDICATION),
                document.getString(Config.DB.COLUMN_SALES_FORM));
    }

    public ObjectId getId() {
        return id;
    }

    @JsonProperty("id")
    public String getIdAsString() {
        return id == null ? "" : id.toString();
    }

    @JsonProperty("name")
    public String getMedicineName() {
        return medicineName;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description == null ? "" : description;
    }

    @JsonProperty("indication")
    public String getIndication() {
        return indication;
    }

    @JsonProperty("contraindication")
    public String getContraindication() {
        return contraindication;
    }

    @JsonProperty("sales_form")
    public String getSalesForm() {
        return salesForm;
    }

    @Override
    public String toString() {
        return getMedicineName() + "\n" + getSalesForm() + "\n" + getContraindication() + "\n" + getIndication();
    }
}
