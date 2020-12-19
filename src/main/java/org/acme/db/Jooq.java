package org.acme.db;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderKeywordStyle;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

import static org.jooq.conf.ParamType.INLINED;
import static org.jooq.conf.RenderNameStyle.LOWER;
import static org.jooq.conf.StatementType.PREPARED_STATEMENT;

/**
 * Created by rsen on 25.02.15.
 */
@ApplicationScoped
public class Jooq {

    private static final SQLDialect SQL_DIALECT = SQLDialect.POSTGRES;

    @Inject
    DataSource dataSource;

    public DSLContext getDslContext() {
        Settings settings = new Settings();
        settings.withRenderSchema(false)
            .withRenderFormatted(true)
            .withRenderNameStyle(LOWER)
            .withRenderKeywordStyle(RenderKeywordStyle.LOWER)
            .withParamType(INLINED)
            .withStatementType(PREPARED_STATEMENT)
            .withExecuteLogging(true)
            .withExecuteWithOptimisticLocking(false)
            .withAttachRecords(true)
            .withUpdatablePrimaryKeys(false);

        return DSL.using(dataSource, SQL_DIALECT, settings);
    }

}
