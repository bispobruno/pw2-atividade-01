package br.edu.ifg.sistemacomercial.bean.converter;

import br.edu.ifg.sistemacomercial.entity.Categoria;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass=Categoria.class)
public class CategoriaConverter implements Converter <Categoria> {
    
    @Override
    public Categoria getAsObject(FacesContext context, UIComponent component, String value) {
      if(value!=null && !"".equals(value)){  
        Categoria categoria = (Categoria)component.getAttributes().get("categoria_"+value);
        return categoria;
    }
      return null;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Categoria value) {
        if(value!=null&&value.getId()!=null){
            component.getAttributes().put("categoria_"+value.getId(),value);
            return value.getId().toString();
        }
        return "";
    }
   
}
