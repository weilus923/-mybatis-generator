package org.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ValidationPlugin extends PluginAdapter {

    private Map<String,Object> jsr303;

    public boolean validate(List<String> list) {
        File jsrConfigFile = new File(System.getProperty("user.dir")+ "\\src\\main\\resources\\mybatis-jsr303.yml");
        boolean validate = jsrConfigFile.exists();
        if(validate){
            FileInputStream in = null;
            try {
                in = new FileInputStream(jsrConfigFile);
                jsr303 = new Yaml().load(in);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if(in != null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return validate;
    }

    @Override
    public boolean modelFieldGenerated(final Field field,
                                       final TopLevelClass topLevelClass,
                                       IntrospectedColumn column,
                                       IntrospectedTable table,
                                       ModelClassType modelClassType) {
        if(jsr303.containsKey(table.getBaseRecordType())){
            Map<String,List<String>> jsrField = (Map<String, List<String>>) jsr303.get(table.getBaseRecordType());
            if(jsrField.containsKey(column.getJavaProperty())){
                List<String> annotations = jsrField.get(column.getJavaProperty());
                for (String annotation:annotations) {
                    topLevelClass.addImportedType(importType(annotation));
                    field.addAnnotation(annotation);
                }
            }
        }
        return super.modelFieldGenerated(field, topLevelClass, column, table, modelClassType);
    }

    private String importType(String annotation){
        if(annotation.startsWith("@AssertFalse"))return "javax.validation.constraints.AssertFalse";
        if(annotation.startsWith("@AssertTrue"))return "javax.validation.constraints.AssertTrue";
        if(annotation.startsWith("@DecimalMax"))return "javax.validation.constraints.DecimalMax";
        if(annotation.startsWith("@DecimalMin"))return "javax.validation.constraints.DecimalMin";
        if(annotation.startsWith("@Digits"))return "javax.validation.constraints.Digits";
        if(annotation.startsWith("@Email"))return "javax.validation.constraints.Email";
        if(annotation.startsWith("@Future"))return "javax.validation.constraints.Future";
        if(annotation.startsWith("@FutureOrPresent"))return "javax.validation.constraints.FutureOrPresent";
        if(annotation.startsWith("@Max"))return "javax.validation.constraints.Max";
        if(annotation.startsWith("@Min"))return "javax.validation.constraints.Min";
        if(annotation.startsWith("@Negative"))return "javax.validation.constraints.Negative";
        if(annotation.startsWith("@NegativeOrZero"))return "javax.validation.constraints.NegativeOrZero";
        if(annotation.startsWith("@NotBlank"))return "javax.validation.constraints.NotBlank";
        if(annotation.startsWith("@NotEmpty"))return "javax.validation.constraints.NotEmpty";
        if(annotation.startsWith("@NotNull"))return "javax.validation.constraints.NotNull";
        if(annotation.startsWith("@Null"))return "javax.validation.constraints.Null";
        if(annotation.startsWith("@Past"))return "javax.validation.constraints.Past";
        if(annotation.startsWith("@PastOrPresent"))return "javax.validation.constraints.PastOrPresent";
        if(annotation.startsWith("@Pattern"))return "javax.validation.constraints.Pattern";
        if(annotation.startsWith("@Positive"))return "javax.validation.constraints.Positive";
        if(annotation.startsWith("@PositiveOrZero"))return "javax.validation.constraints.PositiveOrZero";
        if(annotation.startsWith("@Size"))return "javax.validation.constraints.Size";

        if(annotation.startsWith("@CodePointLength"))return "org.hibernate.validator.constraints.CodePointLength";
        if(annotation.startsWith("@ConstraintComposition"))return "org.hibernate.validator.constraints.ConstraintComposition";
        if(annotation.startsWith("@CreditCardNumber"))return "org.hibernate.validator.constraints.CreditCardNumber";
        if(annotation.startsWith("@Currency"))return "org.hibernate.validator.constraints.Currency";
        if(annotation.startsWith("@EAN"))return "org.hibernate.validator.constraints.EAN";
//        if(annotation.startsWith("@Email"))return "org.hibernate.validator.constraints.Email";
        if(annotation.startsWith("@ISBN"))return "org.hibernate.validator.constraints.ISBN";
        if(annotation.startsWith("@Length"))return "org.hibernate.validator.constraints.Length";
        if(annotation.startsWith("@LuhnCheck"))return "org.hibernate.validator.constraints.LuhnCheck";
        if(annotation.startsWith("@Mod10Check"))return "org.hibernate.validator.constraints.Mod10Check";
        if(annotation.startsWith("@Mod11Check"))return "org.hibernate.validator.constraints.Mod11Check";
//        if(annotation.startsWith("@ModCheck"))return "org.hibernate.validator.constraints.ModCheck";
//        if(annotation.startsWith("@NotBlank"))return "org.hibernate.validator.constraints.NotBlank";
//        if(annotation.startsWith("@NotEmpty"))return "org.hibernate.validator.constraints.NotEmpty";
        if(annotation.startsWith("@ParameterScriptAssert"))return "org.hibernate.validator.constraints.ParameterScriptAssert";
        if(annotation.startsWith("@Range"))return "org.hibernate.validator.constraints.Range";
        if(annotation.startsWith("@SafeHtml"))return "org.hibernate.validator.constraints.SafeHtml";
        if(annotation.startsWith("@ScriptAssert"))return "org.hibernate.validator.constraints.ScriptAssert";
        if(annotation.startsWith("@UniqueElements"))return "org.hibernate.validator.constraints.UniqueElements";
        if(annotation.startsWith("@URL"))return "org.hibernate.validator.constraints.URL";

        if(annotation.startsWith("@CNPJ"))return "org.hibernate.validator.constraints.br.CNPJ";
        if(annotation.startsWith("@CPF"))return "org.hibernate.validator.constraints.br.CPF";
        if(annotation.startsWith("@TituloEleitoral"))return "org.hibernate.validator.constraints.br.TituloEleitoral";

        if(annotation.startsWith("@NIP"))return "org.hibernate.validator.constraints.pl.NIP";
        if(annotation.startsWith("@PESEL"))return "org.hibernate.validator.constraints.pl.PESEL";
        if(annotation.startsWith("@REGON"))return "org.hibernate.validator.constraints.pl.REGON";

        if(annotation.startsWith("@DurationMax"))return "org.hibernate.validator.constraints.time.DurationMax";
        if(annotation.startsWith("@DurationMin"))return "org.hibernate.validator.constraints.time.DurationMin";
        else return null;
    }
}
