package cz.muni.fi.pa165.smartphonEShop.dao;

import cz.muni.fi.pa165.smartphonEShop.entity.Address;
import cz.muni.fi.pa165.smartphonEShop.enums.AddressEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Stefan Holecko
 * Class represents:
 */

@Repository
public class AddressDaoImpl implements AddressDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Address address) {
        if (address == null){
            throw new IllegalArgumentException("Address is null!");
        }
        entityManager.persist(address);
    }

    @Override
    public void update(Address address) {
        if (address == null){
            throw new IllegalArgumentException("Address is null!");
        }
        entityManager.merge(address);
    }

    @Override
    public void delete(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address is null!");
        }
        if (entityManager.contains(address)) {
            entityManager.remove(address);

        } else {
            Address a = entityManager.getReference(address.getClass(),address.getId());
            entityManager.remove(a);
        }
    }

    @Override
    public Address findById(Long id) {
        if (id == null || id < 0){
            throw new IllegalArgumentException("AddressId is null or less than 0!");
        }
        return entityManager.find(Address.class, id);
    }

    @Override
    public List<Address> findAll() {
        return entityManager.createQuery("select a from Address a", Address.class)
                .getResultList();
    }

    @Override
    public List<Address> findAllAddressesBy(HashMap<AddressEnum, String> specificator) {
        Set<Address> resultset = new HashSet<>();
        if(specificator.containsKey(AddressEnum.CITY)){
            Set<Address> temp = new HashSet<>();

            temp.addAll(entityManager.createQuery("select a from Address a where a.city = :city", Address.class).
                    setParameter("city", specificator.get(AddressEnum.CITY)).
                    getResultList());

            if(resultset.isEmpty()) {
                resultset.addAll(temp);
            }
            else {
                resultset.removeAll(temp);
            }
        }
        if(specificator.containsKey(AddressEnum.COUNTRY)){
            Set<Address> temp = new HashSet<>();

            temp.addAll(entityManager.createQuery("select a from Address a where a.country = :country", Address.class).
                    setParameter("country", specificator.get(AddressEnum.CITY)).
                    getResultList());

            if(resultset.isEmpty()) {
                resultset.addAll(temp);
            }
            else {
                resultset.removeAll(temp);
            }
        }
        if(specificator.containsKey(AddressEnum.STREET_NAME)){
            Set<Address> temp = new HashSet<>();

            temp.addAll(entityManager.createQuery("select a from Address a where a.streetName = :streetName", Address.class).
                    setParameter("streetName", specificator.get(AddressEnum.CITY)).
                    getResultList());

            if(resultset.isEmpty()) {
                resultset.addAll(temp);
            }
            else {
                resultset.removeAll(temp);
            }
        }
        if(specificator.containsKey(AddressEnum.STREET_NUMBER)){
            Set<Address> temp = new HashSet<>();

            temp.addAll(entityManager.createQuery("select a from Address a where a.streetNumber = :streetNumber", Address.class).
                    setParameter("streetNumber", specificator.get(AddressEnum.CITY)).
                    getResultList());

            if(resultset.isEmpty()) {
                resultset.addAll(temp);
            }
            else {
                resultset.removeAll(temp);
            }
        }
        return resultset.stream().collect(Collectors.toList());
    }
}
