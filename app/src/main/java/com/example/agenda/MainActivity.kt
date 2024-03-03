package com.example.agenda

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    //private lateinit var calendarView: DatePickerDialog.OnDateSetListener
    private lateinit var calendarView: CalendarView
    private lateinit var timePicker: TimePickerDialog.OnTimeSetListener
    private lateinit var addAppointmentButton: Button
    private lateinit var selectedDate: Calendar
    private var selectate: String = ""
    private var appointmentTitle: String = ""
    private var appointmentDescription: String = ""
    private val appointments: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*calendarView.OnDateSetListener { _, year, month, dayOfMonth ->
            selectedDate.set(Calendar.YEAR, year)
            selectedDate.set(Calendar.MONTH, month)
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            showTimePicker()
        }*/
        calendarView = findViewById(R.id.calendarView)


        timePicker = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
            selectedDate.set(Calendar.MINUTE, minute)
            showAddAppointmentDialog()
        }

        selectedDate= Calendar.getInstance()

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            selectate = selectedDate
            //selectedDate.set(year, month, dayOfMonth)
            // Utilisez la variable selectedDate comme vous le souhaitez
            // Par exemple, affichez-la dans un Toast ou effectuez d'autres opérations
            //Toast.makeText(this, selectedDate, Toast.LENGTH_SHORT).show()
        }

        /* calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
             val calendar = Calendar.getInstance()
             calendar.set(year, month, dayOfMonth)

             val selectedDate = SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(calendar.time)
             val selectedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)


                 val dialogBuilder = AlertDialog.Builder(this)
                     .setTitle("Détails du rendez-vous")
                     .setMessage(
                         "Date : $selectedDate\n" +
                                 "Heure : $selectedTime\n" +
                                 "Titre : $appointmentTitle\n" +
                                 "Description : $appointmentDescription"
                     )
                     .setPositiveButton("OK") { dialog, which -> dialog.dismiss() }

                 val alertDialog = dialogBuilder.create()
                 alertDialog.show()

         }
     */

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            selectate = selectedDate

            val appointment = appointments.find { it.startsWith("Rendez-vous le $selectedDate") }
            if (appointment != null) {
                val dialogBuilder = AlertDialog.Builder(this)
                    .setTitle("Détails du rendez-vous")
                    .setMessage(appointment)
                    .setPositiveButton("OK") { dialog, which -> dialog.dismiss() }

                val alertDialog = dialogBuilder.create()
                alertDialog.show()
            }
        }


        addAppointmentButton = findViewById(R.id.add_appointment_button)
        addAppointmentButton.setOnClickListener {
            showTimePicker()
        }
    }

    /*private fun showDatePicker() {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, calendarView, year, month, day)
        datePickerDialog.show()
    }
    */


    private fun showTimePicker() {
        val hour = selectedDate.get(Calendar.HOUR_OF_DAY)
        val minute = selectedDate.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, timePicker, hour, minute, true)
        timePickerDialog.show()
    }



    @SuppressLint("MissingInflatedId")
    private fun showAddAppointmentDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_appointment, null)
        val dialog2View= layoutInflater.inflate(R.layout.dialog_add_conf, null)

        val alertDialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Ajouter un rendez-vous")

        alertDialogBuilder.setPositiveButton("Ajouter") { dialog, which ->
            val editTextTitle = dialogView.findViewById<EditText>(R.id.edit_text_title)
            val editTextDescription = dialogView.findViewById<EditText>(R.id.edit_text_description)

            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()

            val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            val appointmentDate = selectate
            val appointmentTime = timeFormat.format(selectedDate.time)
            val appointmentTitle = title
            val appointmentDescription = description

            val appointment = "Rendez-vous le ${selectate} à ${timeFormat.format(selectedDate.time)} : $title - $description"
            Toast.makeText(this, appointment, Toast.LENGTH_SHORT).show()
            appointments.add(appointment)

            dialog.dismiss()


            val alertDialogDetails = androidx.appcompat.app.AlertDialog.Builder(this)
                .setView(dialog2View)
                .setTitle("Confirmation")

            val dateConfirm = dialog2View.findViewById<TextView>(R.id.confirm_date)
            val heureConfirm = dialog2View.findViewById<TextView>(R.id.confirm_time)
            val titleConfirm = dialog2View.findViewById<TextView>(R.id.confirm_title)
            val descriptionConfirm = dialog2View.findViewById<TextView>(R.id.confirm_description)

            dateConfirm.text = selectate
            heureConfirm.text = timeFormat.format(selectedDate.time)
            titleConfirm.text = title
            descriptionConfirm.text = description


            alertDialogDetails.setPositiveButton("OK"){ dialog, which ->

                /*val dateConfirm = dialog2View.findViewById<EditText>(R.id.confirm_date)
                val heureConfirm = dialog2View.findViewById<EditText>(R.id.confirm_time)
                val titleConfirm = dialog2View.findViewById<EditText>(R.id.confirm_title)
                val descriptionConfirm = dialog2View.findViewById<EditText>(R.id.confirm_description)

                Toast.makeText(this, "saluuuuuuuuuuuuuuut"+selectate+"+"+timeFormat.format(selectedDate.time)+"+"+title+"+"+description,Toast.LENGTH_SHORT).show()
                dateConfirm.setText(selectate)
                heureConfirm.setText(timeFormat.format(selectedDate.time))
                titleConfirm.setText(title)
                //dateConfirm.text = appointment
                //dateConfirm.text = selectate
                //heureConfirm.text = timeFormat.format(selectedDate.time)
                //titleConfirm.text = title
                //descriptionConfirm.text = description
*/
                val appointment = "Rendez-vous le $selectate à ${timeFormat.format(selectedDate.time)} : $title - $description"
                //appointments.add(appointment)

                Toast.makeText(this, "cest bonnn", Toast.LENGTH_SHORT).show()

            }
            alertDialogDetails.setNegativeButton("Annuler"){ dialog, which ->
                dialog.dismiss()
            }

            val alertDialog2 = alertDialogDetails.create()
            alertDialog2.show()


        }

        alertDialogBuilder.setNegativeButton("Annuler") { dialog, which ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }





}