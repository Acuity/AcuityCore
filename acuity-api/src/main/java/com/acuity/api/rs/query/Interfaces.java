package com.acuity.api.rs.query;

import com.acuity.api.AcuityInstance;
import com.acuity.api.rs.wrappers.interfaces.InterfaceComponent;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Interfaces {

    private static Logger logger = LoggerFactory.getLogger(Interfaces.class);

    public static Stream<InterfaceComponent> streamLoaded(){
        return Arrays.stream(AcuityInstance.getClient().getInterfaces())
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull);
    }

    public static Stream<InterfaceComponent> streamLoaded(int rootIndex){
        return Arrays.stream(AcuityInstance.getClient().getInterfaces()[rootIndex])
                .filter(Objects::nonNull);
    }

    /**
     *
     * @param rootIndex
     * @param childPath
     * @return InterfaceComponent, looked up by the finding the
     * interface by index in the Interfaces, then the InterfaceComponent
     * is inside the array returned by the parent.
     */
    public static Optional<InterfaceComponent> get(int rootIndex, int... childPath) {
        Preconditions.checkNotNull(childPath, "Make sure you are passing a least one child index.");
        Preconditions.checkArgument(childPath.length >= 1, "Make sure you are passing a least one child index.");

        InterfaceComponent[][] interfaces = AcuityInstance.getClient().getInterfaces();
        if (interfaces == null || rootIndex < 0 || rootIndex > interfaces.length - 1){
            return Optional.empty();
        }

        InterfaceComponent result = null;
        InterfaceComponent[] interfaceComponents = interfaces[rootIndex];
        for (int childIndex : childPath) {
            if (interfaceComponents == null || childIndex < 0 || childIndex > interfaceComponents.length - 1){
                return Optional.empty();
            }

            result = interfaceComponents[childIndex];
            interfaceComponents = result.getComponents();
        }

        return Optional.ofNullable(result);
    }
}
