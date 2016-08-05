package com.krixi.sqlitecrud;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // show no.of records
        countRecords();
        // display all records
        readRecords();

        Button btn_CreateEmployee = (Button) findViewById(R.id.btnCreateEmployee);
        btn_CreateEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View formElementsView = inflater.inflate(R.layout.employee_input_form, null, false);

                final EditText edtTxtEmpName = (EditText) formElementsView.findViewById(R.id.editTextEmployeeFirstname);
                final EditText edtTxtEmpEmail = (EditText) formElementsView.findViewById(R.id.editTextEmployeeEmail);
                final EditText edtTxtEmpRelocate = (EditText) formElementsView.findViewById(R.id.editTextEmployeeRelocate);

                new AlertDialog.Builder(context)
                        .setView(formElementsView)
                        .setTitle("Create Employee")
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        String employeeName = edtTxtEmpName.getText().toString();
                                        String employeeEmail = edtTxtEmpEmail.getText().toString();
                                        String employeeRelocate = edtTxtEmpRelocate.getText().toString();

                                        Employee objectOfEmployee = new Employee();
                                        objectOfEmployee.empName = employeeName;
                                        objectOfEmployee.empEmail = employeeEmail;
                                        objectOfEmployee.empRelocate = employeeRelocate;

                                        boolean successfulInsertion = new EmployeeController(context).addEmployee(objectOfEmployee);

                                        if (successfulInsertion)
                                        {
                                            Toast.makeText(context, "Employee Info Saved..", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(context, "Unable to Save.", Toast.LENGTH_SHORT).show();
                                        }
                                        countRecords();
                                        readRecords();
                                    }

                                }).show();
            }
        });
    }

    // Below method will count the number of records in the table employees.
    public void countRecords()
    {
        int recordCount = new EmployeeController(this).count();

        TextView txtViewRecordCount = (TextView) findViewById(R.id.tvRecordCount);
        txtViewRecordCount.setText(recordCount + " records found");
    }

    // Below method is used to display all the employee details.

    public void readRecords()
    {
        LinearLayout layoutForRecords = (LinearLayout) findViewById(R.id.ll_Records);
        layoutForRecords.removeAllViews();

        ArrayList<Employee> employeeRecords = new EmployeeController(this).getAllEmployees();

        if (employeeRecords.size() > 0)
        {
            for (Employee emp:employeeRecords)
            {
                int id = emp.id;
                String empName = emp.empName;
                String empEmail = emp.empEmail;
                String empRelocation = emp.empRelocate;

                String employeeContents = empName + "--- " + empEmail + "--- " + empRelocation;

                TextView tvEmployeeItem = new TextView(this);
                tvEmployeeItem.setPadding(0, 20, 0, 20);
                tvEmployeeItem.setText(employeeContents);
                tvEmployeeItem.setTag(Integer.toString(id));
                tvEmployeeItem.setOnLongClickListener(new OnLongClickEmployee());
                layoutForRecords.addView(tvEmployeeItem);
            }
        }
        else
        {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");
            layoutForRecords.addView(locationItem);
        }
    }
}
