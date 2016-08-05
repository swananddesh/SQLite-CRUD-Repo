package com.krixi.sqlitecrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;


/**
 * Created by KS114 on 8/4/16.
 */

/* This class is designed to manage CRUD Operations.
* */
public class EmployeeController extends DatabaseHandler
{
    boolean rowAffected;

    public EmployeeController(Context context)
    {
        super(context);
    }

    // adding new record to the table.
    public boolean addEmployee(Employee employee)
    {
        ContentValues valuesToInsert = new ContentValues();
        valuesToInsert.put("empName", employee.empName);
        valuesToInsert.put("empEmail", employee.empEmail);
        valuesToInsert.put("empRelocate", employee.empRelocate);

        SQLiteDatabase db = this.getWritableDatabase();
        rowAffected = db.insert("employees", null, valuesToInsert) > 0;
        db.close();

        return rowAffected;
    }

    // counting no.of records in the table.
    public int count()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM employees";
        int recordCount = db.rawQuery(query, null).getCount();
        db.close();

        return recordCount;
    }

    // fetching all the employees
    public ArrayList<Employee> getAllEmployees()
    {
        ArrayList<Employee> allEmployee = new ArrayList<>();
        String query = "SELECT * FROM employees ORDER BY id";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorForFetchingAllEmployees = db.rawQuery(query, null);

        if (cursorForFetchingAllEmployees.moveToFirst())
        {
            do
            {
                int id = Integer.parseInt(cursorForFetchingAllEmployees.getString(cursorForFetchingAllEmployees.getColumnIndex("id")));
                String emp_name = cursorForFetchingAllEmployees.getString(cursorForFetchingAllEmployees.getColumnIndex("empName"));
                String emp_email = cursorForFetchingAllEmployees.getString(cursorForFetchingAllEmployees.getColumnIndex("empEmail"));
                String emp_relocation = cursorForFetchingAllEmployees.getString(cursorForFetchingAllEmployees.getColumnIndex("empRelocate"));

                Employee employee = new Employee();
                employee.id = id;
                employee.empName = emp_name;
                employee.empEmail = emp_email;
                employee.empRelocate = emp_relocation;

                allEmployee.add(employee);
            } while (cursorForFetchingAllEmployees.moveToNext());
        }
        cursorForFetchingAllEmployees.close();
        db.close();

        return allEmployee;
    }

    public Employee readSingleRecord(int employeeID)
    {
        Employee employee = null;
        String query = "SELECT * FROM employees WHERE id = " + employeeID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String empname = cursor.getString(cursor.getColumnIndex("empName"));
            String email = cursor.getString(cursor.getColumnIndex("empEmail"));
            String relocate = cursor.getString(cursor.getColumnIndex("empRelocate"));

            employee = new Employee();
            employee.id = id;
            employee.empName = empname;
            employee.empEmail = email;
            employee.empRelocate = relocate;
        }
        cursor.close();
        db.close();
        return employee;
    }

    // updating the selected record
    public boolean updateEmployee(Employee employee)
    {
        ContentValues valuesToUPdate = new ContentValues();
        valuesToUPdate.put("empName", employee.empName);
        valuesToUPdate.put("empEmail", employee.empEmail);
        valuesToUPdate.put("empRelocate", employee.empRelocate);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(employee.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("employees", valuesToUPdate, where, whereArgs) > 0;
        db.close();
        return updateSuccessful;
    }

    //deleting the selected record
    public boolean deleteEmployee(int empID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean deleteSuccessful = db.delete("employees", "id = '" + empID + "'", null) > 0;
        db.close();
        return deleteSuccessful;
    }
}
