package br.com.zupacademy.peter.ecommerce.validador;

import br.com.zupacademy.peter.ecommerce.validador.anotacao.UniqueValue;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String domainAttribute;
    private Class<?> klass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(UniqueValue params) {
        domainAttribute = params.fieldName();
        klass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String q = String.format("SELECT 1 FROM %s WHERE %s=:value", klass.getName(), domainAttribute);
        Query query = manager.createQuery(q);
        query.setParameter("value", value);

        List<?> result = query.getResultList();
        Assert.state(result.size() <= 1, String.format("Banco de Dados contém mais de um %s que deveria ser único. Tabela: %s.", domainAttribute, klass.getName()));

        return result.isEmpty();
    }
}