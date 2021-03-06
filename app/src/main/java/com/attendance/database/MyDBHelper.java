package com.attendance.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.attendance.activities.DailyAttendanceActivity;
import com.attendance.data_models.Attendance;
import com.attendance.data_models.ClassData;
import com.attendance.data_models.Student;
import com.attendance.data_models.StudentData;
import com.attendance.data_models.Teacher;
import com.attendance.data_models.TeacherData;
import com.attendance.fragments.AddClassFragment;
import com.attendance.fragments.AddStudentFragment;
import com.attendance.fragments.AddTeacherFragment;
import com.attendance.fragments.StudentDialogFragment;
import com.attendance.fragments.StudentViewReportFragment;
import com.attendance.fragments.TeacherDialogFragment;
import com.attendance.fragments.TeacherLoginFragment;
import com.attendance.fragments.ClassDialogFragment;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    /*TODO : Database Name*/
    private static final String DATABASE_NAME = "AttendanceApp.db";

    /*TODO: database version*/
	private static final int DATABASE_VERSION = 1;

    //  TODO: below are the tables and columns name identifier
    private static final String PRIMARY_ID = "primaryId";

	// TODO: Admin login Column and tables
	private static final String ADMIN_TABLE_NAME = "adminLogin";
	private static final String ADMIN_USERNAME = "adminUserName";
	private static final String ADMIN_PASSWORD = "adminPassword";

	// TODO: Teacher login tables
	private static final String TEACHER_LOGIN_TABLE_NAME = "teacherLogin";
	private static final String TEACHER_LOGIN_USERNAME = "teacherUserName";
	private static final String TEACHER_LOGIN_PASSWORD = "teacherPassword";

	// TODO: Add Class Details Tables
	private static final String CLASS_TABLE_NAME = "classDetails";
	private static final String CLASS_COURSE_NAME = "courseName";
    private static final String CLASS_SEMESTER = "semester";
    private static final String CLASS_TEACHER_NAME = "teacherName";
    private static final String CLASS_TEACHER_EMAIL_ID = "teacherEmailId";

	// TODO: Teacher Details Tables
    private static final String TEACHER_TABLE_NAME = "teacherDetails";
    private static final String TEACHER_NAME = "teacherName";
    private static final String TEACHER_MOBILE_NUMBER = "teacherMobileNo";
    private static final String TEACHER_EMAIL_ID = "teacherEmailId";
    private static final String TEACHER_PASSWORD = "teacherPassword";
    private static final String TEACHER_QUALIFICATION = "teacherQualification";

	// TODO: Student Details Tables
	private static final String STUDENT_TABLE_NAME = "studentDetails";
	private static final String STUDENT_NAME = "studentName";
	private static final String STUDENT_CLASS = "studentClass";
	private static final String STUDENT_MOBILE_NO = "studentMobileNo";
	private static final String STUDENT_EMAIL_ID = "studentEmailId";

	// TODO: Daily Attendance Details Tables
	private static final String ATTENDANCE_TABLE_NAME = "attendanceDetails";
	private static final String ATTENDANCE_TEACHER_NAME = "teacherName";
	private static final String ATTENDANCE_TEACHER_EMAIL_ID = "teacherEmailId";
	private static final String ATTENDANCE_STUDENT_NAME = "studentName";
	private static final String ATTENDANCE_STUDENT_EMAIL_ID = "studentEmailId";
	private static final String ATTENDANCE_DATE = "attendanceDate";
	private static final String ATTENDANCE_MONTH = "attendanceMonth";
	private static final String ATTENDANCE_YEAR = "attendanceYear";
	private static final String ATTENDANCE_MINIMUM_PERCENTAGE = "minimumPercentage";
	private static final String ATTENDANCE_CLASS_NAME = "className";
	private static final String ATTENDANCE_TOTAL_STUDENT = "totalStudent";
	private static final String ATTENDANCE_STUDENT_STATUS = "studentStatus";

    private static final String TEMP_TABLE_NAME = "replicaTable";

	// TODO: 08/06/2018 work in progress in db changes
	private static final String SESSION_COLUMN_LOG = "log";
	private static final String SESSION_COLUMN_LOG1 = "log1";


    private ArrayList< String > adminColumnList =  new ArrayList<>();
    private ArrayList< String > teacherLoginColumnList =  new ArrayList<>();
    private ArrayList< String > classColumnList =  new ArrayList<>();
    private ArrayList< String > teacherColumnList =  new ArrayList<>();
    private ArrayList< String > studentColumnList =  new ArrayList<>();
    private ArrayList< String > attendanceColumnList =  new ArrayList<>();

    private static MyDBHelper instance;

    private MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.getReadableDatabase();
    }

    public static synchronized MyDBHelper getInstance(Context context) {
        if ( instance == null ) {
            instance = new MyDBHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	try {
    		listOfAllTables();
    	    switch ( DATABASE_VERSION ) {
                case 1:
					db.execSQL( createTableQuery( ADMIN_TABLE_NAME, adminColumnList ) );
					db.execSQL( createTableQuery( TEACHER_LOGIN_TABLE_NAME, teacherLoginColumnList ) );
					db.execSQL( createTableQuery( CLASS_TABLE_NAME, classColumnList ) );
					db.execSQL( createTableQuery( TEACHER_TABLE_NAME, teacherColumnList ) );
					db.execSQL( createTableQuery( STUDENT_TABLE_NAME, studentColumnList ) );
					db.execSQL( createTableQuery( ATTENDANCE_TABLE_NAME, attendanceColumnList ) );
                    break;

//				case 2:
//					// TODO: 08/06/2018 work in progress in db changes
//					sessionColumnList.add("log");
//					db.execSQL( createTableQuery( SESSION_TABLE_NAME, sessionColumnList ) );
//					break;
            }
	    } catch ( Exception e ) {
    		e.getMessage();
	    }
    }

	/**
	 * this method contain lists of all the tables required for application
	 */
	private void listOfAllTables () {
		/**************** Todo: admin table ***********************/
		if ( adminColumnList.size() == 0 ) {
			adminColumnList.add(ADMIN_USERNAME);
			adminColumnList.add(ADMIN_PASSWORD);
		}

		/**************** Todo: teacher login table ***********************/
		if ( teacherLoginColumnList.size() == 0 ) {
			teacherLoginColumnList.add(TEACHER_LOGIN_USERNAME);
			teacherLoginColumnList.add(TEACHER_LOGIN_PASSWORD);
		}

		/**************** Todo: class details table ***********************/
		if ( classColumnList.size() == 0 ) {
			classColumnList.add(CLASS_COURSE_NAME);
			classColumnList.add(CLASS_SEMESTER);
			classColumnList.add(CLASS_TEACHER_NAME);
			classColumnList.add(CLASS_TEACHER_EMAIL_ID);
		}

		/**************** Todo: teacher details table ***********************/
		if ( teacherColumnList.size() == 0 ) {
			teacherColumnList.add(TEACHER_NAME);
			teacherColumnList.add(TEACHER_MOBILE_NUMBER);
			teacherColumnList.add(TEACHER_EMAIL_ID);
			teacherColumnList.add(TEACHER_PASSWORD);
			teacherColumnList.add(TEACHER_QUALIFICATION);
		}

		/**************** Todo: student details table ***********************/
		if ( studentColumnList.size() == 0 ) {
			studentColumnList.add(STUDENT_NAME);
			studentColumnList.add(STUDENT_CLASS);
			studentColumnList.add(STUDENT_MOBILE_NO);
			studentColumnList.add(STUDENT_EMAIL_ID);
		}

		/**************** Todo: attendance details table ***********************/
		if ( attendanceColumnList.size() == 0 ) {
			attendanceColumnList.add(ATTENDANCE_TEACHER_NAME);
			attendanceColumnList.add(ATTENDANCE_TEACHER_EMAIL_ID);
			attendanceColumnList.add(ATTENDANCE_STUDENT_NAME);
			attendanceColumnList.add(ATTENDANCE_STUDENT_EMAIL_ID);
			attendanceColumnList.add(ATTENDANCE_DATE);
			attendanceColumnList.add(ATTENDANCE_MONTH);
			attendanceColumnList.add(ATTENDANCE_YEAR);
			attendanceColumnList.add(ATTENDANCE_CLASS_NAME);
			attendanceColumnList.add(ATTENDANCE_MINIMUM_PERCENTAGE);
			attendanceColumnList.add(ATTENDANCE_STUDENT_STATUS);
			attendanceColumnList.add(ATTENDANCE_TOTAL_STUDENT);
		}
	}

	/**
	 * this method is used for create table query string for when Application newly install (initial state)
	 * @param TableName table name which want to create
	 * @param columnList list of all the column name that we create in table
	 * @return return queryString
	 */
    private String createTableQuery(String TableName, ArrayList< String > columnList) {
	    StringBuilder createQueryString = new StringBuilder();
	    int index = 0;
	    if ( columnList.size() > 0 ) {
		    createQueryString.append("create table ").append(TableName).append("(").append(PRIMARY_ID)
				    .append(" integer primary key autoincrement, ");
		    for ( String columnName : columnList ) {
			    index++;
			    if ( index == columnList.size() ) {
				    createQueryString.append(columnName).append(" text )");
			    }
			    else {
				    createQueryString.append(columnName).append(" text, ");
			    }
		    }
	    }
	    return createQueryString.toString();
    }

	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		listOfAllTables();
//        switch (oldVersion) {
//            case 1:
            	/**************************************** TODO : SESSION TABLE ****************************************/

//            	//todo: create temp.table to hold data
//                db.execSQL("create table " + TEMP_TABLE_NAME + "(primaryId integer primary key autoincrement, id text, sessionKey text, formId text, timeStamp text)");
//                //todo: insert data from old table into temp table
//                db.execSQL("INSERT INTO " + TEMP_TABLE_NAME + " Select *  FROM " + SESSION_TABLE_NAME);
//                //TODO: drop the old table now that your data is safe in the temporary one
//                dropTable(db, SESSION_TABLE_NAME);
//                //TODO: recreate the table this time with all 4 columns
//                db.execSQL("CREATE TABLE " + SESSION_TABLE_NAME + "(primaryId integer primary key autoincrement,id text, sessionKey text, formId text, timeStamp text , abc text)");
//                //todo: fill it up using null for the column_3
//                db.execSQL("INSERT INTO " + SESSION_TABLE_NAME + " Select id, sessionKey, formId, timeStamp, null FROM " + TEMP_TABLE_NAME);
//                //todo: then drop the temporary table
//                dropTable(db, TEMP_TABLE_NAME);

//				ArrayList < String > newColumnList = new ArrayList<>();
//				newColumnList.add(SESSION_COLUMN_LOG);
//				newColumnList.add(SESSION_COLUMN_LOG1);
//				updateTables(db, SESSION_TABLE_NAME, sessionColumnList, newColumnList);
//
//                /**************************************** TODO : PREFIX_TABLE_NAME ************************************/

//                if ( newVersion == 2 ) break;
//
//			case 2:
//
//				break;
//        }

    }

    private void updateTables(SQLiteDatabase db, String TABLE_NAME, ArrayList< String > columnList, ArrayList< String > newColumnList) {
    	try {
			StringBuilder createQueryString = new StringBuilder();
			StringBuilder selectedColumnString = new StringBuilder();
			int lastIndex = 0;
    		if ( columnList.size() > 0 ) {
				selectedColumnString.append(PRIMARY_ID).append(", ");
				for ( String columnName : columnList ) {
					lastIndex++;
					if ( lastIndex == columnList.size() ) {
						createQueryString.append(columnName).append(" text ");
					} else {
						createQueryString.append(columnName).append(" text, ");
					}
					selectedColumnString.append(columnName).append(", ");
				}

				//todo: create temp.table to hold data
				db.execSQL("create table " + TEMP_TABLE_NAME + "(primaryId integer primary key autoincrement," + createQueryString + ")");
				//todo: insert data from old table into temp table
				db.execSQL("INSERT INTO " + TEMP_TABLE_NAME + " Select *  FROM " + TABLE_NAME);
				//TODO: drop the old table now that your data is safe in the temporary one
				dropTable(db, TABLE_NAME);

				int index = 0;
				if ( newColumnList.size() > 0 ) {
					for ( String newColumnName : newColumnList ) {
						index++;
						if ( newColumnList.size() == 1 ) {
							createQueryString.append(", ").append(newColumnName).append(" text");
							selectedColumnString.append(" '"+"' ");
						}
						else if ( index == newColumnList.size() ) {
							createQueryString.append(newColumnName).append(" text");
							selectedColumnString.append(" '"+"' ");
						} else {
							createQueryString.append(", ").append(newColumnName).append(" text, ");
							selectedColumnString.append(" '"+"' ").append(", ");
						}
					}
				}

				//TODO: recreate the table this time with all n columns
				db.execSQL("CREATE TABLE " + TABLE_NAME + "(primaryId integer primary key autoincrement," + createQueryString + ")");
				//todo: fill it up using null for the column n
				db.execSQL("INSERT INTO " + TABLE_NAME + " Select " + selectedColumnString + " FROM " + TEMP_TABLE_NAME);
				//todo: then drop the temporary table
				dropTable(db, TEMP_TABLE_NAME);
			}
		} catch ( Exception e ) {
//    		e.getMessage();
		}
	}

    private void dropTable(SQLiteDatabase db, String tableName) {
        db.execSQL("DROP TABLE " + tableName);
    }



	public boolean insertTeacherData(AddTeacherFragment.AddTeacherData data) {
    	try {
		    SQLiteDatabase db = this.getWritableDatabase();
		    ContentValues contentValues = new ContentValues();
		    contentValues.put(TEACHER_NAME, data.teacherName);
		    contentValues.put(TEACHER_MOBILE_NUMBER, data.phone);
		    contentValues.put(TEACHER_EMAIL_ID, data.email);
		    contentValues.put(TEACHER_PASSWORD, data.password);
		    contentValues.put(TEACHER_QUALIFICATION, data.qualification);

		    String _selection = TEACHER_EMAIL_ID + " = ? ";
		    String[] _selectionToArgs = {data.email};
		    String[] _colToReturn = {"*"};
		    Cursor cursor = db.query(TEACHER_TABLE_NAME, _colToReturn, _selection,
				    _selectionToArgs, null, null, null);
		    if ( cursor.getCount() > 0 ) {
			    cursor.close();
			    return false;
		    }
		    else {
			    db.insert(TEACHER_TABLE_NAME, null, contentValues);
			    cursor.close();
			    return true;
		    }
	    } catch ( Exception e ) {
    		e.getMessage();
	    }
	    return false;
	}

	public ArrayList<TeacherData > getTeacherData() {
    	ArrayList<TeacherData> dataList = new ArrayList<>();
    	SQLiteDatabase db = this.getReadableDatabase();
    	try {
		    String[] _colToReturn = {CLASS_TEACHER_NAME,CLASS_COURSE_NAME};
		    Cursor cursor = db.query(CLASS_TABLE_NAME, _colToReturn, null,
				    null, null, null, null);
		    if ( cursor.getCount() > 0 ) {
		    	cursor.moveToFirst();
		    	while ( !cursor.isAfterLast() ) {
					TeacherData data = new TeacherData();
					data.setTeacherName(getString(cursor, CLASS_TEACHER_NAME));
					data.setCourseName(getString(cursor, CLASS_COURSE_NAME));
					dataList.add(data);
					cursor.moveToNext();
			    }
			    cursor.close();
		    	return dataList;
		    }
	    } catch ( Exception e ) {
    		e.getMessage();
	    }
	    return dataList;
	}

	public boolean updateTeacherData(TeacherDialogFragment.EditTeacherData data) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			String _selection = CLASS_TEACHER_NAME + " = ? AND " + CLASS_COURSE_NAME + " = ?" ;
			String[] _selectionToArgs = { data.getRowData().getTeacherName(),
					                     data.getRowData().getCourseName() };
			String[] _colToReturn = {CLASS_TEACHER_NAME, CLASS_TEACHER_EMAIL_ID};
			Cursor cursor = db.query(CLASS_TABLE_NAME, _colToReturn, _selection,
					_selectionToArgs, null, null, null);
			if ( cursor.getCount() > 0 ) {
				cursor.moveToFirst();
				String _teacherName = getString(cursor, CLASS_TEACHER_NAME);
				String _teacherEmailId = getString(cursor, CLASS_TEACHER_EMAIL_ID);

				if (!TextUtils.isEmpty(_teacherName) &&
						!TextUtils.isEmpty(_teacherEmailId)) {
					String selection = TEACHER_EMAIL_ID + " = ? ";
					String[] args = {_teacherEmailId};
					String[] returnCol = {TEACHER_EMAIL_ID};
					Cursor res = db.query(TEACHER_TABLE_NAME, returnCol, selection, args,
							null, null, null);
					if ( res.getCount() > 0 ) {
						ContentValues values = new ContentValues();
						values.put(TEACHER_NAME, data.getTeacherName());
						values.put(TEACHER_MOBILE_NUMBER, data.getPhone());
						values.put(TEACHER_QUALIFICATION, data.getQualification());
						db.update(TEACHER_TABLE_NAME, values, selection, args);

						ContentValues classValues = new ContentValues();
						classValues.put(CLASS_TEACHER_NAME, data.getTeacherName());
						db.update(CLASS_TABLE_NAME, classValues, _selection, _selectionToArgs);
						res.close();
						return true;
					}
				}
				cursor.close();
				return false;
			} else {
				cursor.close();
				return false;
			}
		} catch ( Exception e ) {
			e.getMessage();
		}
		return false;
	}

	public boolean checkTeacherEmail(TeacherLoginFragment.TeacherLoginData data) {

		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor cursor = null;
//		String query = "SELECT * FROM "+TEACHER_TABLE_NAME+" WHERE " + TEACHER_EMAIL_ID +"=? AND" + TEA;
//		cursor= db.rawQuery(query,new String[]{data._email});

        String _selection = TEACHER_EMAIL_ID + " = ? AND " + TEACHER_PASSWORD + " = ?" ;
        String[] _selectionToArgs = {data._email, data._password};
        String[] _colToReturn = {"*"};
        Cursor cursor = db.query(TEACHER_TABLE_NAME, _colToReturn, _selection,
                _selectionToArgs, null, null, null);

		if(cursor.getCount() > 0) {
			cursor.close();
			return true;
		} else {
			cursor.close();
			return false;
		}
	}

	public ArrayList<Teacher> getTeacherEmail() {
    	ArrayList<Teacher> _array_list = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			Cursor _cursor = db.query(TEACHER_TABLE_NAME, null, null, null, null, null, null);
			if ( _cursor.getCount() > 0 ) {
				_cursor.moveToFirst();
				while ( !_cursor.isAfterLast() ) {
					Teacher _data = new Teacher();
					_data.email = getString(_cursor,TEACHER_EMAIL_ID);
					_data.name = getString(_cursor,TEACHER_NAME);
					_array_list.add(_data);
					_cursor.moveToNext();
				}
				_cursor.close();
				return  _array_list;
			}
			_cursor.close();
			return  _array_list;
		} catch ( Exception e ) {
			e.getMessage();
		}
		return  _array_list;
	}

	public boolean insertClassData(AddClassFragment.AddClassData data) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			contentValues.put(CLASS_COURSE_NAME, data.courseName);
			contentValues.put(CLASS_SEMESTER, data.semester);
			contentValues.put(CLASS_TEACHER_NAME, data.teacherName);
			contentValues.put(CLASS_TEACHER_EMAIL_ID, data.teacherEmail);

			String _selection = CLASS_COURSE_NAME + " = ? AND " + CLASS_SEMESTER + " = ?" ;
			String[] _selectionToArgs = {data.courseName, data.semester};
			String[] _colToReturn = {"*"};
			Cursor cursor = db.query(CLASS_TABLE_NAME, _colToReturn, _selection,
					_selectionToArgs, null, null, null);
			if ( cursor.getCount() > 0 ) {
				cursor.close();
				return false;
			}
			else {
				db.insert(CLASS_TABLE_NAME, null, contentValues);
				cursor.close();
				return true;
			}
		} catch ( Exception e ) {
			e.getMessage();
		}
        return false;
	}

	public ArrayList<String> getCourseList(String teacherEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> courseList = new ArrayList<>();
        try {
            String[] _colToReturn = {"*"};
            String _selection = CLASS_TEACHER_EMAIL_ID +" =?";
            String[] _selectionArgs = {teacherEmail};
            Cursor cursor = db.query(CLASS_TABLE_NAME, _colToReturn, _selection, _selectionArgs, null, null, null);
	        if ( cursor.getCount() > 0 ) {
		        cursor.moveToFirst();
		        while ( !cursor.isAfterLast() ) {
			        courseList.add(getString(cursor, CLASS_COURSE_NAME).trim());
			        cursor.moveToNext();
		        }
	        }
            cursor.close();
        } catch ( Exception e ) {
            e.getMessage();
        }
        return courseList;
    }

	public ArrayList<ClassData > getClassData() {
		ArrayList<ClassData> dataList = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			String[] _colToReturn = {CLASS_COURSE_NAME, CLASS_SEMESTER};
			Cursor cursor = db.query(CLASS_TABLE_NAME, _colToReturn, null,
					null, null, null, null);
			if ( cursor.getCount() > 0 ) {
				cursor.moveToFirst();
				while ( !cursor.isAfterLast() ) {
					ClassData data = new ClassData();
					data.setCourseName(getString(cursor, CLASS_COURSE_NAME));
					data.setSemester(getString(cursor, CLASS_SEMESTER));
					dataList.add(data);
					cursor.moveToNext();
				}
				cursor.close();
				return dataList;
			}
			cursor.close();
			return dataList;
		} catch ( Exception e ) {
			e.getMessage();
		}
		return dataList;
	}

	public boolean updateClassData(ClassDialogFragment.EditClassData data) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			String _selection = CLASS_COURSE_NAME + " = ? AND " + CLASS_SEMESTER + " = ?" ;
			String[] _selectionToArgs = {data.getRowData().getCourseName(),
					data.getRowData().getSemester()};
			String[] _colToReturn = {PRIMARY_ID, CLASS_COURSE_NAME, CLASS_SEMESTER};
			Cursor cursor = db.query(CLASS_TABLE_NAME, _colToReturn, _selection,
					_selectionToArgs, null, null, null);
			if ( cursor.getCount() > 0 ) {
				cursor.moveToFirst();
				ContentValues values =  new ContentValues();
				values.put(CLASS_TEACHER_EMAIL_ID, data.getTeacherEmailId());
				values.put(CLASS_TEACHER_NAME, data.getTeacherName());

				if ( !TextUtils.isEmpty(getString(cursor, PRIMARY_ID)) ) {
					String primaryId = getString(cursor, PRIMARY_ID);
					String selection = PRIMARY_ID + " = ?";
					String[] selectionToArgs = {primaryId};
					db.update(CLASS_TABLE_NAME, values, selection, selectionToArgs);
					cursor.close();
					return true;
				}
				cursor.close();
				return false;
			} else {
				cursor.close();
				return false;
			}
		} catch ( Exception e ) {
			e.getMessage();
		}
		return false;
	}

	public boolean insertStudentData(AddStudentFragment.AddStudentData data) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			contentValues.put(STUDENT_CLASS, data.className);
			contentValues.put(STUDENT_EMAIL_ID, data.email);
			contentValues.put(STUDENT_MOBILE_NO, data.phone);
			contentValues.put(STUDENT_NAME, data.studentName);

			String _selection = STUDENT_EMAIL_ID + " = ? ";
			String[] _selectionToArgs = {data.email};
			String[] _colToReturn = {"*"};
			Cursor cursor = db.query(STUDENT_TABLE_NAME, _colToReturn, _selection,
					_selectionToArgs, null, null, null);

			if ( cursor.getCount() > 0 ) {
				cursor.close();
				return false;
			}
			else {
				db.insert(STUDENT_TABLE_NAME, null, contentValues);
				cursor.close();
				return true;
			}
		} catch ( Exception e ) {
			e.getMessage();
		}
		return false;
	}

	public boolean updateStudentData(StudentDialogFragment.EditStudentData data) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			String _selection = STUDENT_EMAIL_ID + " = ? ";
			String[] _selectionToArgs = {data.getStudentData().getStudentEmailId()};
			String[] _colToReturn = {STUDENT_EMAIL_ID};
			Cursor cursor = db.query(STUDENT_TABLE_NAME, _colToReturn, _selection,
					_selectionToArgs, null, null, null);
			if ( cursor.getCount() > 0 ) {
				cursor.moveToFirst();
				ContentValues values =  new ContentValues();
				values.put(STUDENT_NAME, data.getStudentName());
				values.put(STUDENT_CLASS, data.getStudentCourseName());
				values.put(STUDENT_MOBILE_NO, data.getStudentPhone());
				db.update(STUDENT_TABLE_NAME, values, _selection, _selectionToArgs);
				cursor.close();
				return true;
			} else {
				cursor.close();
				return false;
			}
		} catch ( Exception e ) {
			e.getMessage();
		}
		return false;
	}

    public ArrayList<Student> getStudentList(String courseName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Student> studentList = new ArrayList<>();
        try {
            String[] _colToReturn = {"*"};
            String _selection = STUDENT_CLASS +" =?";
            String[] _selectionArgs = {courseName};
            Cursor cursor = db.query(STUDENT_TABLE_NAME, _colToReturn, _selection, _selectionArgs, null, null, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                	Student student = new Student();
                    student.studentName = getString(cursor, STUDENT_NAME).trim();
                    student.studentEmail = getString(cursor, STUDENT_EMAIL_ID).trim();
					studentList.add(student);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch ( Exception e ) {
            e.getMessage();
        }
        return studentList;
    }

	public ArrayList<String> getStudentEmailId() {
    	ArrayList<String> emailIdList = new ArrayList<>();
    	SQLiteDatabase db = this.getReadableDatabase();
    	try {
    		String[] _colToReturn = {STUDENT_EMAIL_ID};
    		Cursor cursor = db.query(STUDENT_TABLE_NAME, _colToReturn, null,
				    null, null, null, null);
		    if ( cursor.getCount() > 0 ) {
		    	cursor.moveToFirst();
			    while ( !cursor.isAfterLast() ) {
			    	emailIdList.add(getString(cursor, STUDENT_EMAIL_ID));
			    	cursor.moveToNext();
			    }
			    cursor.close();
			    return  emailIdList;
		    }
		    cursor.close();
		    return  emailIdList;
	    } catch ( Exception e ) {
    		e.getMessage();
	    }
	    return emailIdList;
    }

	public ArrayList<StudentData > getStudentData() {
		ArrayList<StudentData> dataList = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			String[] _colToReturn = {STUDENT_NAME, STUDENT_CLASS, STUDENT_EMAIL_ID};
			Cursor cursor = db.query(STUDENT_TABLE_NAME, _colToReturn, null,
					null, null, null, null);
			if ( cursor.getCount() > 0 ) {
				cursor.moveToFirst();
				while ( !cursor.isAfterLast() ) {
					StudentData data = new StudentData();
					data.setStudentName(getString(cursor, STUDENT_NAME));
					data.setClassName(getString(cursor, STUDENT_CLASS));
					data.setStudentEmailId(getString(cursor, STUDENT_EMAIL_ID));
					dataList.add(data);
					cursor.moveToNext();
				}
				cursor.close();
				return dataList;
			}
		} catch ( Exception e ) {
			e.getMessage();
		}
		return dataList;
	}

    public boolean insertAttendanceData(DailyAttendanceActivity.AttendanceData data) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ATTENDANCE_CLASS_NAME, data.course);
            contentValues.put(ATTENDANCE_STUDENT_NAME, data.studentName);
            contentValues.put(ATTENDANCE_STUDENT_EMAIL_ID, data.studentEmail);
            contentValues.put(ATTENDANCE_STUDENT_STATUS, data.attendanceStatus);
            contentValues.put(ATTENDANCE_TOTAL_STUDENT, data.totalStudent);
            contentValues.put(ATTENDANCE_DATE, data.day);
            contentValues.put(ATTENDANCE_MONTH, data.month);
            contentValues.put(ATTENDANCE_YEAR, data.year);

            db.insert(ATTENDANCE_TABLE_NAME, null, contentValues);
            return true;
        } catch ( Exception e ) {
            e.getMessage();
        }
        return false;
    }

    public ArrayList<String> getStudentAttendanceReport(StudentViewReportFragment.StudentReport
		                                                        report){
    	ArrayList<String> list = new ArrayList<>();
    	return list;
    }

    public ArrayList<Attendance> getCourseSpecificStudentData() {
        ArrayList<Attendance> attendanceArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String _selection = ATTENDANCE_STUDENT_STATUS + " IN(?,?) AND " + ATTENDANCE_CLASS_NAME + "=?";
            String[] _selectionToArgs = {"Present", "Late", "2D-Design"};
            String[] _colToReturn = {ATTENDANCE_STUDENT_EMAIL_ID, ATTENDANCE_STUDENT_NAME, "count(*)"};
            Cursor cursor = db.query(ATTENDANCE_TABLE_NAME, _colToReturn, _selection,
                    _selectionToArgs, ATTENDANCE_STUDENT_EMAIL_ID, null, null);

            if ( cursor.getCount() > 0 ) {
                cursor.moveToFirst();
                while ( !cursor.isAfterLast() ) {
                    Attendance data = new Attendance();
                    data.name = getString(cursor, ATTENDANCE_STUDENT_NAME);
                    data.email = getString(cursor, ATTENDANCE_STUDENT_EMAIL_ID);
                    attendanceArrayList.add(data);
                    cursor.moveToNext();
                }
                cursor.close();
                return attendanceArrayList;
            }
        } catch ( Exception e ) {
            e.getMessage();
        }
        return attendanceArrayList;

    }

	public String getString(Cursor cursor, String column) {
		try {
			int index = cursor.getColumnIndex(column);
			if ( index != -1 ) {
				return cursor.getString(index);
			}
		} catch ( Exception e ) {
			e.getMessage();
		}
		return "";
	}

	public long getLong(Cursor cursor, String column) {
		try {
			int index = cursor.getColumnIndex(column);
			if ( index != -1 ) {
				return cursor.getLong(index);
			}
		} catch ( Exception e ) {
			e.getMessage();
		}
		return -1;
	}

	public int getInt(Cursor cursor, String column) {
		try {
			int index = cursor.getColumnIndex(column);
			if ( index != -1 ) {
				return cursor.getInt(index);
			}
		} catch ( Exception e ) {
			e.getMessage();
		}
		return -1;
	}

	private class JsonParsing<T> {
		private Object fromJson(String data, Class<T> tClass) throws JsonParseException {
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(data);
			return new Gson().fromJson(jsonElement, tClass);
		}
	}
}


