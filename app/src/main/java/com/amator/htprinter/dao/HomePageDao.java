package com.amator.htprinter.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.amator.htprinter.module.HomePage;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HOME_PAGE".
*/
public class HomePageDao extends AbstractDao<HomePage, Long> {

    public static final String TABLENAME = "HOME_PAGE";

    /**
     * Properties of entity HomePage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Db_index = new Property(0, long.class, "db_index", true, "_id");
        public final static Property ApkLink = new Property(1, String.class, "apkLink", false, "APK_LINK");
        public final static Property Author = new Property(2, String.class, "author", false, "AUTHOR");
        public final static Property ChapterId = new Property(3, int.class, "chapterId", false, "CHAPTER_ID");
        public final static Property ChapterName = new Property(4, String.class, "chapterName", false, "CHAPTER_NAME");
        public final static Property Collect = new Property(5, boolean.class, "collect", false, "COLLECT");
        public final static Property CourseId = new Property(6, int.class, "courseId", false, "COURSE_ID");
        public final static Property Desc = new Property(7, String.class, "desc", false, "DESC");
        public final static Property EnvelopePic = new Property(8, String.class, "envelopePic", false, "ENVELOPE_PIC");
        public final static Property Fresh = new Property(9, boolean.class, "fresh", false, "FRESH");
        public final static Property Id = new Property(10, int.class, "id", false, "ID");
        public final static Property Link = new Property(11, String.class, "link", false, "LINK");
        public final static Property NiceDate = new Property(12, String.class, "niceDate", false, "NICE_DATE");
        public final static Property Origin = new Property(13, String.class, "origin", false, "ORIGIN");
        public final static Property ProjectLink = new Property(14, String.class, "projectLink", false, "PROJECT_LINK");
        public final static Property PublishTime = new Property(15, long.class, "publishTime", false, "PUBLISH_TIME");
        public final static Property SuperChapterId = new Property(16, int.class, "superChapterId", false, "SUPER_CHAPTER_ID");
        public final static Property SuperChapterName = new Property(17, String.class, "superChapterName", false, "SUPER_CHAPTER_NAME");
        public final static Property Title = new Property(18, String.class, "title", false, "TITLE");
        public final static Property Type = new Property(19, int.class, "type", false, "TYPE");
        public final static Property Visible = new Property(20, int.class, "visible", false, "VISIBLE");
        public final static Property Zan = new Property(21, int.class, "zan", false, "ZAN");
    }


    public HomePageDao(DaoConfig config) {
        super(config);
    }
    
    public HomePageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HOME_PAGE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: db_index
                "\"APK_LINK\" TEXT," + // 1: apkLink
                "\"AUTHOR\" TEXT," + // 2: author
                "\"CHAPTER_ID\" INTEGER NOT NULL ," + // 3: chapterId
                "\"CHAPTER_NAME\" TEXT," + // 4: chapterName
                "\"COLLECT\" INTEGER NOT NULL ," + // 5: collect
                "\"COURSE_ID\" INTEGER NOT NULL ," + // 6: courseId
                "\"DESC\" TEXT," + // 7: desc
                "\"ENVELOPE_PIC\" TEXT," + // 8: envelopePic
                "\"FRESH\" INTEGER NOT NULL ," + // 9: fresh
                "\"ID\" INTEGER NOT NULL ," + // 10: id
                "\"LINK\" TEXT," + // 11: link
                "\"NICE_DATE\" TEXT," + // 12: niceDate
                "\"ORIGIN\" TEXT," + // 13: origin
                "\"PROJECT_LINK\" TEXT," + // 14: projectLink
                "\"PUBLISH_TIME\" INTEGER NOT NULL ," + // 15: publishTime
                "\"SUPER_CHAPTER_ID\" INTEGER NOT NULL ," + // 16: superChapterId
                "\"SUPER_CHAPTER_NAME\" TEXT," + // 17: superChapterName
                "\"TITLE\" TEXT," + // 18: title
                "\"TYPE\" INTEGER NOT NULL ," + // 19: type
                "\"VISIBLE\" INTEGER NOT NULL ," + // 20: visible
                "\"ZAN\" INTEGER NOT NULL );"); // 21: zan
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HOME_PAGE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HomePage entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getDb_index());
 
        String apkLink = entity.getApkLink();
        if (apkLink != null) {
            stmt.bindString(2, apkLink);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
        stmt.bindLong(4, entity.getChapterId());
 
        String chapterName = entity.getChapterName();
        if (chapterName != null) {
            stmt.bindString(5, chapterName);
        }
        stmt.bindLong(6, entity.getCollect() ? 1L: 0L);
        stmt.bindLong(7, entity.getCourseId());
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(8, desc);
        }
 
        String envelopePic = entity.getEnvelopePic();
        if (envelopePic != null) {
            stmt.bindString(9, envelopePic);
        }
        stmt.bindLong(10, entity.getFresh() ? 1L: 0L);
        stmt.bindLong(11, entity.getId());
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(12, link);
        }
 
        String niceDate = entity.getNiceDate();
        if (niceDate != null) {
            stmt.bindString(13, niceDate);
        }
 
        String origin = entity.getOrigin();
        if (origin != null) {
            stmt.bindString(14, origin);
        }
 
        String projectLink = entity.getProjectLink();
        if (projectLink != null) {
            stmt.bindString(15, projectLink);
        }
        stmt.bindLong(16, entity.getPublishTime());
        stmt.bindLong(17, entity.getSuperChapterId());
 
        String superChapterName = entity.getSuperChapterName();
        if (superChapterName != null) {
            stmt.bindString(18, superChapterName);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(19, title);
        }
        stmt.bindLong(20, entity.getType());
        stmt.bindLong(21, entity.getVisible());
        stmt.bindLong(22, entity.getZan());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HomePage entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getDb_index());
 
        String apkLink = entity.getApkLink();
        if (apkLink != null) {
            stmt.bindString(2, apkLink);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
        stmt.bindLong(4, entity.getChapterId());
 
        String chapterName = entity.getChapterName();
        if (chapterName != null) {
            stmt.bindString(5, chapterName);
        }
        stmt.bindLong(6, entity.getCollect() ? 1L: 0L);
        stmt.bindLong(7, entity.getCourseId());
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(8, desc);
        }
 
        String envelopePic = entity.getEnvelopePic();
        if (envelopePic != null) {
            stmt.bindString(9, envelopePic);
        }
        stmt.bindLong(10, entity.getFresh() ? 1L: 0L);
        stmt.bindLong(11, entity.getId());
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(12, link);
        }
 
        String niceDate = entity.getNiceDate();
        if (niceDate != null) {
            stmt.bindString(13, niceDate);
        }
 
        String origin = entity.getOrigin();
        if (origin != null) {
            stmt.bindString(14, origin);
        }
 
        String projectLink = entity.getProjectLink();
        if (projectLink != null) {
            stmt.bindString(15, projectLink);
        }
        stmt.bindLong(16, entity.getPublishTime());
        stmt.bindLong(17, entity.getSuperChapterId());
 
        String superChapterName = entity.getSuperChapterName();
        if (superChapterName != null) {
            stmt.bindString(18, superChapterName);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(19, title);
        }
        stmt.bindLong(20, entity.getType());
        stmt.bindLong(21, entity.getVisible());
        stmt.bindLong(22, entity.getZan());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public HomePage readEntity(Cursor cursor, int offset) {
        HomePage entity = new HomePage( //
            cursor.getLong(offset + 0), // db_index
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // apkLink
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // author
            cursor.getInt(offset + 3), // chapterId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // chapterName
            cursor.getShort(offset + 5) != 0, // collect
            cursor.getInt(offset + 6), // courseId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // desc
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // envelopePic
            cursor.getShort(offset + 9) != 0, // fresh
            cursor.getInt(offset + 10), // id
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // link
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // niceDate
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // origin
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // projectLink
            cursor.getLong(offset + 15), // publishTime
            cursor.getInt(offset + 16), // superChapterId
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // superChapterName
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // title
            cursor.getInt(offset + 19), // type
            cursor.getInt(offset + 20), // visible
            cursor.getInt(offset + 21) // zan
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HomePage entity, int offset) {
        entity.setDb_index(cursor.getLong(offset + 0));
        entity.setApkLink(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAuthor(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setChapterId(cursor.getInt(offset + 3));
        entity.setChapterName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCollect(cursor.getShort(offset + 5) != 0);
        entity.setCourseId(cursor.getInt(offset + 6));
        entity.setDesc(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setEnvelopePic(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setFresh(cursor.getShort(offset + 9) != 0);
        entity.setId(cursor.getInt(offset + 10));
        entity.setLink(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setNiceDate(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setOrigin(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setProjectLink(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setPublishTime(cursor.getLong(offset + 15));
        entity.setSuperChapterId(cursor.getInt(offset + 16));
        entity.setSuperChapterName(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setTitle(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setType(cursor.getInt(offset + 19));
        entity.setVisible(cursor.getInt(offset + 20));
        entity.setZan(cursor.getInt(offset + 21));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(HomePage entity, long rowId) {
        entity.setDb_index(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(HomePage entity) {
        if(entity != null) {
            return entity.getDb_index();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(HomePage entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
