package org.zalando.intellij.swagger.completion.value;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.value.model.StringValue;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.completion.CompletionHelper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class SecurityScopeNameValueCompletion extends ValueCompletion {

    SecurityScopeNameValueCompletion(final CompletionHelper completionHelper,
                                     final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    @Override
    public void fill() {
        completionHelper.getParentKeyName().ifPresent(parentKeyName ->
                getSecurityDefinitionByName(parentKeyName).forEach(this::addValue)
        );
    }

    private List<Value> getSecurityDefinitionByName(final String securityDefinitionName) {
        return completionHelper.getChildrenOfRoot("securityDefinitions").stream()
                .filter(el -> securityDefinitionName.equals(completionHelper.getKeyNameOfObject(el).orElse(null)))
                .map(completionHelper::getSecurityScopesIfOAuth2)
                .flatMap(Collection::stream)
                .map(StringValue::new)
                .collect(Collectors.toList());
    }

}
