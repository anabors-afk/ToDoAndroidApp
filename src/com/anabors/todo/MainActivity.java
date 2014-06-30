package com.anabors.todo;

import java.util.List;

import com.anabors.todo.data.Choice;
import com.anabors.todo.data.ChoiceDataSource;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	private ChoiceDataSource dataSource;
	private List<Choice> choices;
    private int currentChoiceId;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerForContextMenu(getListView()); 
        dataSource = new ChoiceDataSource(this);
        displayAllChoices();
        bindNewChoiceButton();
    }

    private void displayAllChoices() {
        choices = dataSource.findAll();
        ArrayAdapter<Choice> adapter =
                new ArrayAdapter<Choice>(this, android.R.layout.simple_list_item_1, choices);
        setListAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        currentChoiceId = (int) info.id;
        menu.add(0, 9999, 0, "Complete Task");
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 9999) {
            Choice choice = choices.get(currentChoiceId);
            dataSource.remove(choice);
            displayAllChoices();
        }

        return super.onContextItemSelected(item);
    }
    
    private void bindNewChoiceButton() {
        Button newChoiceButton = (Button) findViewById(R.id.new_choice);
        newChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChoice();
            }
        });
    }
    
    private void createChoice() {
        final Choice choice = new Choice();
        updateChoiceFromInput(choice);
    }
    
    private void updateChoiceFromInput(final Choice choice) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setText(choice.getName());
        alert.setView(input);

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                String updatedName = input.getText().toString();
	                
	                if (updatedName.matches("")) {
	                	Toast.makeText(getApplicationContext(), "Task Cannot Be Blank", Toast.LENGTH_SHORT).show();
	                }
	              	else {
		                choice.setName(updatedName);
	                	 dataSource.save(choice);
	                	 displayAllChoices();
	                }  
	            }
	        });
        
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}
			});

        alert.show();
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Choice choice = choices.get(position);
		updateChoiceFromInput(choice);
	}
}