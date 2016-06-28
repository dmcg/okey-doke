package com.oneeyedmen.okeydoke.sources;

import com.oneeyedmen.okeydoke.Reporter;
import com.oneeyedmen.okeydoke.Sources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClassPathSourceOfApproval extends FileSystemSourceOfApproval {

    private final Package thePackage;

    public ClassPathSourceOfApproval(Package thePackage, File actualDir, Reporter<File> reporter) {
        super(null, actualDir, reporter);
        this.thePackage = thePackage;
    }

    @Override
    protected InputStream inputOrNullForApproved(String testName) throws FileNotFoundException {
        return getClass().getResourceAsStream("/" + Sources.pathForPackage(thePackage) + "/" + testName + approvedExtension());
    }
}
