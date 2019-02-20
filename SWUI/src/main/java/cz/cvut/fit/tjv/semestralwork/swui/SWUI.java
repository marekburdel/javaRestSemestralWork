package cz.cvut.fit.tjv.semestralwork.swui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.DateRenderer;

import cz.cvut.fit.tjv.semestralwork.swrestclient.*;
import cz.cvut.fit.tjv.semestralwork.swclientview.*;
import java.text.DateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;




/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("swtheme")
public class SWUI extends UI {
    

    private final AddressClient addressClient = AddressClient.getInstance();
    private final StreetClient streetClient = StreetClient.getInstance();
    private final PersonClient personClient = PersonClient.getInstance();
    private List<AddressDTO> addresses;
    private List<StreetDTO> streets;
    private List<PersonDTO> persons;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        loadEntitiesToLists(layout);

        //---------------------------------------- street ----------------------------------------
        //---------------------------------------- street ----------------------------------------
        
        Label streetHead = new Label("Streets");
        Grid<StreetDTO> streetGrid = new Grid<>();
        HorizontalLayout addStreetLayout = new HorizontalLayout();
        final Binder<StreetDTO> streetBinder = new Binder<>(StreetDTO.class);
        Label addStreetHead = new Label("Add/edit serie");
        
        setStreetGrid(streetGrid);
        setAddStreetLayoutAndStreetBinder(addStreetLayout, streetBinder);
        
        Button submitStreet = new Button("Add or edit street", e -> {
            StreetDTO s = new StreetDTO();
            if (streetBinder.writeBeanIfValid(s)) {
                streetClient.createOrUpdateJson(s);
                streets.clear();
                streets.addAll(Arrays.asList(streetClient.retrieveAllJson()));
                streetGrid.setItems(streets);
            }
        });
        streetGrid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                streetBinder.readBean(e.getFirstSelectedItem().get());
            }
        });
         
        //---------------------------------------- street addresses ----------------------------------------
        
        Grid<AddressDTO> streetAddressesGrid =  new Grid<>(AddressDTO.class);
        setStreetAddressesGrid(streetAddressesGrid);
        
        streetGrid.addSelectionListener(event -> {
            Optional<StreetDTO> selected = event.getFirstSelectedItem();
            if (selected.isPresent()) {
                streetAddressesGrid.setItems(streetClient.getStreetAddresses(selected.get(), addresses));
            } else {
                streetAddressesGrid.setItems(new ArrayList<>());
            }
        });

        HorizontalLayout streetLayout = new HorizontalLayout(streetGrid, streetAddressesGrid);
        
        layout.addComponents(streetHead, streetLayout);
        layout.addComponents(addStreetHead, addStreetLayout, submitStreet);
    
        
        //---------------------------------------- address ----------------------------------------
        //---------------------------------------- address ----------------------------------------
        
        Label addressHead = new Label("Addresses");
        Grid<AddressDTO> addressGrid = new Grid<>(AddressDTO.class);
        setAddressGrid(addressGrid);
        
        Label addAddressHead = new Label("Add/edit address");
        TextField addressId = new TextField("Address ID");
        TextField addressNumber = new TextField("Number");
 
        HorizontalLayout addAddressLayout = new HorizontalLayout(addressId, addressNumber);
        
        Button submitAddress = new Button("Add or edit address", e -> {
            StreetDTO street = streetGrid.getSelectedItems().stream().findFirst().get();
            AddressDTO a =  new AddressDTO();
            a.setId(Integer.parseInt(addressId.getValue()));
            a.setNumber(Integer.parseInt(addressNumber.getValue()));
            a.setStreet(street);
            addressClient.createOrUpdateJson(a);
            addresses.clear();
            addresses.addAll(Arrays.asList(addressClient.retrieveAllJson()));   
            addressGrid.setItems(addresses);
            });
        addressGrid.setItems(addresses);
        layout.addComponents(addressHead, addressGrid, addAddressHead, addAddressLayout, submitAddress);
        
         //---------------------------------------- person ----------------------------------------
         //---------------------------------------- person ----------------------------------------
         
        Label personHead = new Label("Person");
        Grid<PersonDTO> personGrid = new Grid<>(PersonDTO.class);
        setPersonGrid(personGrid);
           
        Label addPersonHead = new Label("Add/edit person");
        HorizontalLayout addPersonLayout = new HorizontalLayout();
        final Binder<PersonDTO> personBinder = new Binder<>(PersonDTO.class);
        setAddPersonLayoutAndPersonBinder(addPersonLayout, personBinder);    
        
        personGrid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                personBinder.readBean(e.getFirstSelectedItem().get());
            }
        });
        
        Button submitPerson = new Button("Add or edit person", e -> {
            PersonDTO s = new PersonDTO();
            if (personBinder.writeBeanIfValid(s)) {
                personClient.createOrUpdateJson(s);
                persons.clear();
                persons.addAll(Arrays.asList(personClient.retrieveAllJson()));
                personGrid.setItems(persons);
            }
        });
        personGrid.setItems(persons);

         //---------------------------------------- person addresses ----------------------------------------
        
        Grid<AddressDTO> personAddressesGrid =  new Grid<>(AddressDTO.class);
        setPersonAddressesGrid(personAddressesGrid, personGrid);

        personGrid.addSelectionListener(event -> {
            Optional<PersonDTO> selected = event.getFirstSelectedItem();
            if (selected.isPresent()) {
                personAddressesGrid.setItems(selected.get().getAddresses());
            } else {
                personAddressesGrid.setItems(new ArrayList<>());
            }
        });

        Button addToAddressesButton = new Button("Add to person's addresses", e -> {
            PersonDTO person = personGrid.getSelectedItems().stream().findFirst().get();
            AddressDTO address = addressGrid.getSelectedItems().stream().findFirst().get();
            try {
                personClient.addAddressJson(person.getId(), address.getId());
                person.getAddresses().add(address);
                personAddressesGrid.setItems(person.getAddresses());
            } catch (SWServiceException ex) {
                Notification.show("Cannot add this address", Notification.Type.WARNING_MESSAGE);
            }
        });
        addToAddressesButton.setEnabled(false);
        SelectionListener l = e -> addToAddressesButton.setEnabled(addressGrid.getSelectedItems().size() > 0 && personGrid.getSelectedItems().size() > 0);
        addressGrid.addSelectionListener(l);
        personGrid.addSelectionListener(l);

        HorizontalLayout personLayout = new HorizontalLayout(personGrid, addToAddressesButton, personAddressesGrid);
        layout.addComponents(personHead, personLayout, addPersonHead, addPersonLayout, submitPerson);
      
        //-----
        setContent(layout);
    }

    
    
    
    private void loadEntitiesToLists(VerticalLayout layout) {
        try {
            addresses = new ArrayList<>(Arrays.asList(addressClient.retrieveAllJson()));
            streets = new ArrayList<>(Arrays.asList(streetClient.retrieveAllJson()));
            persons = new ArrayList<>(Arrays.asList(personClient.retrieveAllJson()));
        } catch (SWServiceException e) {
            
            Notification.show("SW service is not running " + e, Notification.Type.ERROR_MESSAGE);
            setContent(layout);
            return;
        }
    }

    private void setStreetGrid(Grid<StreetDTO> streetGrid) {
        streetGrid.addColumn(StreetDTO::getId).setCaption("Street ID");
        streetGrid.addColumn(StreetDTO::getName).setCaption("Name");
        streetGrid.addColumn(StreetDTO::getCity).setCaption("City");
//        streetsGrid.addColumn(StreetDTO::getPostalCode).setCaption("Postal Code");
//        streetsGrid.addColumn(StreetDTO::getCountry).setCaption("Country");
        streetGrid.addColumn(s -> "Delete", new ButtonRenderer<>(e -> {
            streetClient.delete(e.getItem().getId());
            streets.remove(e.getItem());
            streetGrid.setItems(streets);
        }));
        streetGrid.setItems(streets);
    }

    private void setAddStreetLayoutAndStreetBinder(HorizontalLayout addStreetLayout, Binder<StreetDTO> streetBinder) {
        
        TextField streetId = new TextField("Street ID");
        TextField streetName = new TextField("Name");
        TextField streetCity = new TextField("City");
        TextField streetPC = new TextField("Postal Code");
        TextField streetCountry = new TextField("Country");
        

        addStreetLayout.addComponents(streetId, streetName, streetCity, streetPC, streetCountry);
        
        streetBinder.forField(streetId).asRequired().withConverter(new StringToIntegerConverter("Must be integer")).bind("id");
        streetBinder.forField(streetName).asRequired().bind("name");
        streetBinder.forField(streetCity).asRequired().bind("city");
        streetBinder.forField(streetPC).asRequired().bind("postalCode");
        streetBinder.forField(streetCountry).asRequired().bind("country");
    }

    private void setStreetAddressesGrid(Grid<AddressDTO> streetAddressesGrid) {
        streetAddressesGrid.removeColumn("persons");
        streetAddressesGrid.removeColumn("street");
        streetAddressesGrid.setColumnOrder("id", "number");
    }

    private void setAddressGrid(Grid<AddressDTO> addressGrid) {
        addressGrid.removeColumn("persons");
        addressGrid.removeColumn("street");
        addressGrid.setColumnOrder("id", "number");
        addressGrid.addColumn(s -> "Delete", new ButtonRenderer<>(e -> {
            addressClient.delete(e.getItem().getId());
            addresses.remove(e.getItem());
            addressGrid.setItems(addresses);
        }));        
    }

    private void setPersonGrid(Grid<PersonDTO> personGrid) {
        personGrid.removeColumn("dateOfBirth");
        personGrid.removeColumn("addresses");
        personGrid.removeColumn("identificationNumber");
        personGrid.removeColumn("id");
        personGrid.addColumn("dateOfBirth", new DateRenderer(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)));
        personGrid.addColumn(s -> "Delete", new ButtonRenderer<>(e -> {
            personClient.delete(e.getItem().getId());
            persons.remove(e.getItem());
            personGrid.setItems(persons);
        }));
    }

    private void setAddPersonLayoutAndPersonBinder(HorizontalLayout addPersonLayout, Binder<PersonDTO> personBinder) {
        TextField personId = new TextField("Person ID");
        TextField personName = new TextField("Name");
        TextField personSurname = new TextField("Surname");
        DateTimeField dateOfBirth = new DateTimeField("Date of Birth");
        TextField identificationNumber = new TextField("ID Number");
        
        
        personBinder.forField(personId).asRequired().withConverter(new StringToIntegerConverter("Must be integer")).bind("id");
        personBinder.forField(personName).asRequired().bind("name");
        personBinder.forField(personSurname).asRequired().bind("surname");
        personBinder.forField(dateOfBirth).withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault())).bind("dateOfBirth");
        personBinder.forField(identificationNumber).asRequired().bind("identificationNumber");
        
        addPersonLayout.addComponents(personId, personName, personSurname, dateOfBirth, identificationNumber);
    }

    private void setPersonAddressesGrid(Grid<AddressDTO> personAddressesGrid, Grid<PersonDTO> personGrid) {
        personAddressesGrid.removeColumn("persons");
        personAddressesGrid.removeColumn("street");
        personAddressesGrid.setColumnOrder("id", "number");
        personAddressesGrid.addColumn(s -> "Remove from addresses", new ButtonRenderer<>(e -> {
            PersonDTO person = personGrid.getSelectedItems().stream().findFirst().get();
            personClient.removeAddressJson(person.getId(), e.getItem().getId());
            person.getAddresses().remove(e.getItem());
            personAddressesGrid.setItems(person.getAddresses());
        }));
    }


    @WebServlet(urlPatterns = "/*", name = "swIUServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = SWUI.class, productionMode = false)
    public static class swIUServlet extends VaadinServlet {
    }
}
