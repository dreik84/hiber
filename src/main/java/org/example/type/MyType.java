package org.example.type;

import org.hibernate.usertype.UserType;

public class MyType implements UserType {

    @Override
    public int getSqlType() {
        return 0;
    }

    @Override
    public Class returnedClass() {
        return null;
    }

    @Override
    public Object deepCopy(Object value) {
        return null;
    }

    @Override
    public boolean isMutable() {
        return false;
    }
}
