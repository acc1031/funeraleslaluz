package funeraleslaluz.com.servicio;

import funeraleslaluz.com.modelo.Usuario;

import java.util.List;

public interface IUserService {

    public List<Usuario> listUser(); // Listar

    public Usuario searchUserById(Long idUser); // Buscar por Id

    public void saveUser(Usuario user); // Guardar

    public void deleteUser(Long idUser); // Borrar

}
