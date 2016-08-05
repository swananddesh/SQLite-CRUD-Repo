package com.krixi.sqlitecrud;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by KS114 on 8/5/16.
 */
public class OnLongClickEmployee implements View.OnLongClickListener
{
    Context context;
    String id;
    @Override
    public boolean onLongClick(View v)
    {
        context = v.getContext();
        id = v.getTag().toString();
        final CharSequence[] options = { "Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Employee Record")
                .setItems(options, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int item)
                    {

                        if (item == 0)
                        {
                            editRecord(Integer.parseInt(id));
                        }
                        else if (item == 1)
                        {
                            boolean deleteSuccessful = new EmployeeController(context).deleteEmployee(Integer.parseInt(id));
                            if (deleteSuccessful)
                            {
                                Toast.makeText(context, "Employee Info Deleted..", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(context, "Unable to Delete", Toast.LENGTH_SHORT).show();
                            }
                            ((MainActivity)context).countRecords();
                            ((MainActivity)context).readRecords();
                        }

                        dialog.dismiss();

                    }
                }).show();
        return false;
    }

    public void editRecord(final int empId)
    {
            final EmployeeController employeeController = new EmployeeController(context);
            final Employee emp = employeeController.readSingleRecord(empId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View formElements = inflater.inflate(R.layout.employee_input_form, null, false);

        final EditText edtTxtEmpName = (EditText) formElements.findViewById(R.id.editTextEmployeeFirstname);
        final EditText edtTxtEmpEmail = (EditText) formElements.findViewById(R.id.editTextEmployeeEmail);
        final EditText edtTxtEmpRelocate = (EditText) formElements.findViewById(R.id.editTextEmployeeRelocate);

        edtTxtEmpName.setText(emp.empName);
        edtTxtEmpEmail.setText(emp.empEmail);
        edtTxtEmpRelocate.setText(emp.empRelocate);

        new AlertDialog.Builder(context)
                .setView(formElements)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                Employee empInfo = new Employee();
                                empInfo.id = empId;
                                empInfo.empName = edtTxtEmpName.getText().toString();
                                empInfo.empEmail = edtTxtEmpEmail.getText().toString();
                                empInfo.empRelocate = edtTxtEmpRelocate.getText().toString();
                                boolean updateSuccessful = employeeController.updateEmployee(empInfo);
                                if (updateSuccessful)
                                {
                                    Toast.makeText(context, "Employee Info Updated..", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(context, "Unable to Update", Toast.LENGTH_SHORT).show();
                                }

                                ((MainActivity)context).countRecords();
                                ((MainActivity)context).readRecords();
                            }

                        }).show();
    }
}
