package auth.entities;

import java.util.List;

public interface DataSet {
    List<User> users();
    List<Claim> claims();
    List<Authorization> authorizations();
}
