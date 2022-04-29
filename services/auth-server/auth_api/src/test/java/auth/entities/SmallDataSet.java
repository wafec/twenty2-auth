package auth.entities;

import java.util.List;

public class SmallDataSet implements DataSet {
    @Override
    public List<User> users() {
        return null;
    }

    @Override
    public List<Claim> claims() {
        return null;
    }

    @Override
    public List<Authorization> authorizations() {
        return null;
    }
}
