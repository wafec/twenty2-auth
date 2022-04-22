using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthServer.Shared.Api
{
    public interface IAuthenticationApi
    {
        object Authenticate(string username, string password);
    }
}
