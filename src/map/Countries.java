/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Countries stores a list of countries that make up the map.
 */
public class Countries implements Serializable 
{
    /**
     * Stores references to the different countries.
     */
    private volatile ArrayList<Country> countries;

    /**
     * Stores references to the country.
     */
    private volatile Country country;
    /**
     * Allows a user to change the list of countries.
     * @param countries An ArrayList of countries.
     */
    public Countries() 
    {
        this.countries = new ArrayList<Country>();
    }
    
    /**
     * Allows a user to change the list of countries.
     * @param countries An ArrayList of countries.
     */
    public synchronized void setCountries(ArrayList<Country> countries) 
    {
        this.countries = countries;
    }

    /**
     * Returns a list of the countries that make up the map.
     * @return Returns a reference to the list of countries.
     */
    public synchronized ArrayList<Country> getCountries() 
    {
        return countries;
    }


    /**
     * Given the name of a country, this method returns a reference to that country.
     * @param name The name of the country.
     * @return Returns a reference to the country.
     */
    public synchronized Country getCountry(String name)
    {
        for(Country c: getCountries())
        {
            if(c.getName().equalsIgnoreCase(name))
            {
                country = c;
            }
        }

        return country;
    }
    
    /**
     * Adds country to the list of countries.
     * @param country The country to be added.
     */
    public synchronized void addCountry(Country country) 
    {
        this.countries.add(country);
    }

    /**
     * Removes country from the list of countries.
     * @param country The country to remove from the list of countries.
     */
    public synchronized void deleteCountry(Country country) 
    {
        countries.remove(country);
    }

}
