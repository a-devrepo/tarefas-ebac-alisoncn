package com.sudy.projeto.domain.factory;

import com.sudy.projeto.domain.model.Persistente;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Factory {
    public abstract Persistente create(ResultSet resultSet) throws SQLException;
}
