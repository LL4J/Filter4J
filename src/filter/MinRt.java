package filter;
// MinRt is a minimal runtime for LL4J deep learning framework
// While filter.MinRt is as small as possible, it is SLOW and INEFFICIENT.
// Use filter.MinRt only when you need to run LL4J models in a memory-constrained environment.
// Copyright (c) 2024 huzpsb [admin<at>huzpsb<dot>eu<dot>org]
// Licensed under the WTFPL license. You may remove this notice at will.

public class MinRt {
    public MinRt() {
        throw new UnsupportedOperationException("This is a static class");
    }

    public static int doAi(double[] input, String[] script) {
        double[] current = new double[input.length];
        System.arraycopy(input, 0, current, 0, input.length);
        for (String str : script) {
            if (str.length() < 2) {
                continue;
            }
            String[] tokens = str.split(" ");
            switch (tokens[0]) {
                case "D":
                    int ic = Integer.parseInt(tokens[1]);
                    int oc = Integer.parseInt(tokens[2]);
                    if (current.length != ic) {
                        throw new RuntimeException("Wrong input size for Dense layer (expected " + ic + ", got " + current.length + ")");
                    }
                    double[] tmp = new double[oc];
                    for (int i = 0; i < oc; i++) {
                        double sum = 0;
                        for (int j = 0; j < ic; j++) {
                            sum += current[j] * Double.parseDouble(tokens[3 + i + j * oc]);
                        }
                        tmp[i] = sum;
                    }
                    current = tmp;
                    break;
                case "L":
                    int n = Integer.parseInt(tokens[1]);
                    if (current.length != n) {
                        throw new RuntimeException("Wrong input size for LeakyRelu layer (expected " + n + ", got " + current.length + ")");
                    }
                    for (int i = 0; i < n; i++) {
                        current[i] = current[i] > 0 ? current[i] : current[i] * 0.01;
                    }
                    break;
                case "J":
                    int m = Integer.parseInt(tokens[1]);
                    if (current.length != m) {
                        throw new RuntimeException("Wrong input size for Judge layer (expected " + m + ", got " + current.length + ")");
                    }
                    int idx = 0;
                    for (int i = 1; i < m; i++) {
                        if (current[i] > current[idx]) {
                            idx = i;
                        }
                    }
                    return idx;
                default:
                    throw new RuntimeException("Unknown layer type");
            }
        }
        throw new RuntimeException("No output layer");
    }
}
