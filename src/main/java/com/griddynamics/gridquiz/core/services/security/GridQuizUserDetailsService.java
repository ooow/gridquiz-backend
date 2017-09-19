package com.griddynamics.gridquiz.core.services.security;

//@Service
public class GridQuizUserDetailsService // implements UserDetailsService {
{
}
   /* //@Autowired
    //private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails loadedUser;

        *//*try {
            User client = userDao.findFirstByEmailOrPhone(username);
            loadedUser = new org.springframework.security.core.userdetails.User(
                    client.getName(), null, DummyAuthority.getAuth());
        } catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }*//*
        return null;
    }

    static class DummyAuthority implements GrantedAuthority {
        static Collection<GrantedAuthority> getAuth() {
            List<GrantedAuthority> res = new ArrayList<>(1);
            res.add(new DummyAuthority());
            return res;
        }

        @Override
        public String getAuthority() {
            return "USER";
        }
    }
}
*/