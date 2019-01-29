package savvy.wit.framework.core.base.service.dao.annotation;


public enum CType {
    CHAR,
    BOOLEAN,
    VARCHAR,
    TEXT,
    BINARY,
    TIMESTAMP,
    DATETIME,
    DATE,
    TIME,
    INT,
    FLOAT,
    PSQL_JSON,
    PSQL_ARRAY,
    MYSQL_JSON,
    AUTO;

    private CType() {

    }
}
