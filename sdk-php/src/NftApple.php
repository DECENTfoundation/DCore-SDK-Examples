<?php

use DCorePHP\Model\Annotation\Modifiable;
use DCorePHP\Model\Annotation\Type;
use DCorePHP\Model\Annotation\Unique;
use DCorePHP\Model\NftModel;

class NftApple extends NftModel
{
    /**
     * @Type("integer")
     */
    public $size;
    /**
     * @Type("string")
     * @Unique
     */
    public $color;
    /**
     * @Type("boolean")
     * @Modifiable("both")
     */
    public $eaten;

    /**
     * NftApple constructor.
     *
     * @param $size
     * @param $color
     * @param $eaten
     */
    public function __construct($size, $color, $eaten)
    {
        $this->size = gmp_init($size);
        $this->color = $color;
        $this->eaten = $eaten;
    }
}