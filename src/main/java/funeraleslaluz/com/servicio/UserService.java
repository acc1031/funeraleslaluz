package funeraleslaluz.com.servicio;

import funeraleslaluz.com.modelo.Usuario;
import funeraleslaluz.com.repositorio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository uRepo;

    @Override
    public List<Usuario> listUser() {
        return uRepo.findAll();
    }

    @Override
    public Usuario searchUserById(Long idUser) {
        Usuario user = uRepo.findById(idUser).orElse(null);
        return user;
    }

    @Override
    public void saveUser(Usuario user) {
        uRepo.save(user);
    }

    @Override
    public void deleteUser(Long idUser) {
        uRepo.deleteById(idUser);
    }
}
