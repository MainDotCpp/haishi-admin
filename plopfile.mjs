export default function Plopfile(plop) {
    plop.setGenerator('pojo', {
        description: 'application controller logic',
        prompts: [{
            type: 'input',
            name: 'package',
            message: '包名'
        },
            {
                type: 'input',
                name: 'name',
                message: '业务名'
            },
            {
                type: 'input',
                name: 'comment',
                message: '注释'
            }
        ],
        actions: [{
            type: 'addMany',
            base: '_templates/service',
            destination: 'src/main/java/com/haishi/admin',
            force: true,
            templateFiles: '_templates/service'
        },
            {
                type: 'append',
                templateFile: '_templates/permission.sql.hbs',
                path: 'sql/permission.sql'
            },
            {
                type: 'append',
                templateFile: '_templates/generate_log.sh.hbs',
                path: 'sql/generate_log.sh'
            },
        ]
    })
};