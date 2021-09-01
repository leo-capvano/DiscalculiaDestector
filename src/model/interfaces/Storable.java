/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.interfaces;

import java.util.List;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 * @param <T> the type to store
 */
public interface Storable<T> {
    public void doSave(T toSave);
    public T doRetrieveById(int id);
    public List<T> doRetrieveAll();
    public T doUpdate(T toUpdate);
    public void doDelete(T toDelete);
}
